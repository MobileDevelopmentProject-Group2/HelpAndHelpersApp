package com.example.helpersapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helpersapp.model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UpdateUserViewModel: ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db:FirebaseFirestore = FirebaseFirestore.getInstance()


   fun updateUserDetail(userId: String,updateUser:User, onComplete:(Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                db.collection("users").document(userId).set(updateUser)
                    .addOnSuccessListener {
                        onComplete(true, "User data updated successfully.")
                    }
                    .addOnFailureListener{
                        e->
                        onComplete(false, "Fail to change User data ${e.message}.")
                    }


            }catch (e:Exception) {
                onComplete(false, "Fail to update user data ${e.message}")
            }
        }}
    //verify user pwd
    fun verifyPwd(email:String, password:String, onComplete: (Boolean) -> Unit) {
        val user = firebaseAuth.currentUser
        if (user != null && email.isNotEmpty() && password.isNotBlank()) {
            val credential = EmailAuthProvider.getCredential(email, password)
            user.reauthenticate(credential).addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    onComplete(true)
                }

            }
        }else {
            onComplete(false)
        }
    }


    fun updateUserEmailAndPwd(newEmail : String, newPassword:String, onComplete: (Boolean, String) -> Unit)
        {
            val user= firebaseAuth.currentUser
            user?.let {
                it.updateEmail(newEmail).addOnCompleteListener{
                    task ->
                    if (task.isSuccessful){
                        it.updatePassword(newPassword).addOnCompleteListener{
                                passwordTask ->
                            if (passwordTask.isSuccessful) {
                                onComplete(true, "Email or password changed successfully")
                            } else {
                                onComplete(false, "Failed to change email or password ${passwordTask.exception?.message}")
                            }
                        }
                    }else {
                        onComplete(false, "Failed to update email: ${task.exception?.message}")
                }
            }
            } ?: onComplete(false, "User not logged in")
    }
}


/*
val updateUserData = mapOf(
    "email" to email,
    "address" to address,
    "lastname" to lastname
)
*/
//val userRef = db.collection("users").document(username.toString())