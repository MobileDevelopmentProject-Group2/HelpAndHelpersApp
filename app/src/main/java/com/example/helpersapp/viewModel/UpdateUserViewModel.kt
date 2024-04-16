package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helpersapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateUserViewModel: ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun updateUserDetail(
        userId: String,
        updateUser:User,
        onComplete:(Boolean, String) -> Unit)
    {
        val userUpdate = mapOf(
            "firstname" to updateUser.firstname,
            "lastname" to updateUser.lastname,
            //"email" to updateUser.email,
            "address" to updateUser.address
        )
        db.collection("users").document(userId).update(userUpdate)
          .addOnSuccessListener {
              onComplete(true, "User data updated successfully.")
              Log.e("FirebaseStorage", "update user data")
                    }
          .addOnFailureListener{
                  exception->
              Log.e("FirebaseStorage", "Error updating user data", exception)
              onComplete(false, "Fail to change User data ${exception.message}.")

                    }
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
/*
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
* */
/*
*
    // Update email and password
    fun updateUserEmailAndPwd(
        newEmail: String,
        newPassword: String,
        currentPassword: String,
        currentPassword1: String,
        onComplete: (Boolean, String) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && newEmail.isNotBlank() && newPassword.isNotBlank() && currentPassword.isNotBlank()) {
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)
            currentUser.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    currentUser.updateEmail(newEmail).addOnCompleteListener { updateEmailTask ->
                        if (updateEmailTask.isSuccessful) {
                            currentUser.updatePassword(newPassword).addOnCompleteListener { updatePasswordTask ->
                                if (updatePasswordTask.isSuccessful) {
                                    onComplete(true, "Email and password updated successfully.")
                                } else {
                                    onComplete(false, "Failed to update password.")
                                }
                            }
                        } else {
                            onComplete(false, "Failed to update email.")
                        }
                    }
                } else {
                    onComplete(false, "Re-authentication failed. Check your password.")
                }
            }
        } else {
            onComplete(false, "Invalid email or password.")
        }
    }*/