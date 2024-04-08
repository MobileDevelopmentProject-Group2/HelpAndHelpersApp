package com.example.helpersapp.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.helpersapp.ui.components.createUsername
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

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

    private val storageReference = Firebase.storage.reference
    fun uploadProfilePicture(uri: Uri) {
        val useremail = Firebase.auth.currentUser?.email
        val username = createUsername(useremail ?: "")
        val profilePictureRef = storageReference.child("$username/profile_picture.jpg")

        profilePictureRef.putFile(uri)
            .addOnSuccessListener {
                Log.d("FirebaseStorage", "Profile picture upload success")
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "Error uploading profile picture", exception)
            }
    }

    fun uploadCertificate(uri: Uri) {
        val useremail = Firebase.auth.currentUser?.email
        val username = createUsername(useremail ?: "")
        val certificateId = UUID.randomUUID().toString()
        val certificateRef = storageReference.child("$username/certificates/$certificateId.pdf")

        certificateRef.putFile(uri)
            .addOnSuccessListener {
                Log.d("FirebaseStorage", "Certificate upload success")
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "Error uploading certificate", exception)
            }
    }
}
