package com.example.helpersapp.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.ui.components.createUsername
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class HelperViewModel: ViewModel()  {

    private val db = FirebaseFirestore.getInstance()

    private val _clickedUsername = MutableStateFlow("")
    val clickedUsername: StateFlow<String> = _clickedUsername.asStateFlow()



    fun saveClickedUsername(username: String) {
        _clickedUsername.value = username
    }

    fun getFullUserName(username: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(username)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val firstName = documentSnapshot.getString("firstname") ?: ""
                    val lastName = documentSnapshot.getString("lastname") ?: ""
                    val fullName = "$firstName $lastName"
                    onSuccess(fullName)
                } else {
                    onFailure(Exception("User document does not exist"))
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getEmailForClickedUser(onSuccess: (String?) -> Unit, onFailure: (Exception) -> Unit) {
        val clickedUsername = _clickedUsername.value
        if (clickedUsername.isNotBlank()) {
            db.collection("users").document(clickedUsername)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val email = documentSnapshot.getString("email")
                        onSuccess(email)
                    } else {
                        onFailure(Exception("User document does not exist"))
                    }
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        } else {
            onFailure(Exception("Clicked username is blank or empty"))
        }
    }

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

    fun getHelperDetails(username: String, onSuccess: (HelperInfo) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("helpers").document(username)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val data = document.data
                    val about = data?.get("about") as String
                    val category = data["category"] as String
                    val helpDetails = data["helpDetails"] as String
                    val experience = data["experience"] as String
                    val helperInfo = HelperInfo(
                        username,
                        about,
                        category,
                        helpDetails,
                        experience,
                        username
                    )
                    onSuccess(helperInfo)
                } else {
                    onFailure(Exception("Document does not exist or is null"))
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getNannies(
        onSuccess: (List<HelperInfo>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users")
            .get()
            .addOnSuccessListener { userDocuments ->
                val userMap = mutableMapOf<String, Pair<String, String>>()

                for (userDocument in userDocuments) {
                    val username = userDocument.id
                    val firstName = userDocument.getString("firstname") ?: ""
                    val lastName = userDocument.getString("lastname") ?: ""
                    userMap[username] = Pair(firstName, lastName)
                }
        db.collection("helpers")
            .get()
            .addOnSuccessListener { documents ->
                val nannyList = mutableListOf<HelperInfo>()
                for (document in documents) {
                    val data = document.data
                    val category = data["category"] as? String ?: ""
                    if (category.contains("Nanny")) {
                        val username = data["username"] as? String ?: ""
                        val about = data["about"] as? String ?: ""
                        val helpDetails = data["helpDetails"] as? String ?: ""
                        val experience = data["experience"] as? String ?: ""

                        val (firstName, lastName) = userMap[username] ?: Pair("", "")
                        val fullName = "$firstName $lastName"

                        val helperInfo = HelperInfo(fullName, about, category, helpDetails, experience,username)
                        nannyList.add(helperInfo)
                    }
                }
                onSuccess(nannyList)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
        }
    }

    fun getTutors(
        onSuccess: (List<HelperInfo>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users")
            .get()
            .addOnSuccessListener { userDocuments ->
                val userMap = mutableMapOf<String, Pair<String, String>>()

                for (userDocument in userDocuments) {
                    val username = userDocument.id
                    val firstName = userDocument.getString("firstname") ?: ""
                    val lastName = userDocument.getString("lastname") ?: ""
                    userMap[username] = Pair(firstName, lastName)
                }
                db.collection("helpers")
                    .get()
                    .addOnSuccessListener { documents ->
                        val tutorList = mutableListOf<HelperInfo>()
                        for (document in documents) {
                            val data = document.data
                            val category = data["category"] as? String ?: ""
                            if (category.contains("Tutor")) {
                                val username = data["username"] as? String ?: ""
                                val about = data["about"] as? String ?: ""
                                val helpDetails = data["helpDetails"] as? String ?: ""
                                val experience = data["experience"] as? String ?: ""

                                val (firstName, lastName) = userMap[username] ?: Pair("", "")
                                val fullName = "$firstName $lastName"

                                val helperInfo = HelperInfo(fullName, about, category, helpDetails, experience,username)
                                tutorList.add(helperInfo)
                            }
                        }
                        onSuccess(tutorList)
                    }
                    .addOnFailureListener { e ->
                        onFailure(e)
                    }
            }
    }
}

