package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.helpersapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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


