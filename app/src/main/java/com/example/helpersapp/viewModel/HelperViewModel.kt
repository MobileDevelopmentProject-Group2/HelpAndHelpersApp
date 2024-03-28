package com.example.helpersapp.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class HelperViewModel: ViewModel()  {

    private val db = FirebaseFirestore.getInstance()

    fun saveUserData(
        about: String,
        category: String,
        helpDetails: String,
        experience: String,
        username: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userData = hashMapOf(
            "about" to about,
            "category" to category,
            "helpDetails" to helpDetails,
            "experience" to experience,
            "username" to username
        )

        db.collection("helpers").document(username)
            .set(userData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

}