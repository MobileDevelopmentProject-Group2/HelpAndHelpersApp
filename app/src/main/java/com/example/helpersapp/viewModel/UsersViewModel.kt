package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.helpersapp.ui.components.createUsername
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class UsersViewModel: ViewModel()  {
    //get the firebase auth
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun createUserIdFromEmail(email: String): String {
        // lowercase email address
        var userIdFromEmail = email.lowercase()
        // remove TLD from email
        userIdFromEmail = userIdFromEmail.substring(0, userIdFromEmail.lastIndexOf('.'))
        // remove . from email
        userIdFromEmail = userIdFromEmail.replace(".", "")
        // remove @ from email
        userIdFromEmail = userIdFromEmail.replace("@", "")

        Log.d("UsersViewModel", "UserId resolved to: $userIdFromEmail")
        return userIdFromEmail
    }

    suspend fun registerUserToDocumentStore(firstname: String, lastname: String, email: String, password: String, address: String): String
    {
        var returnCode = "Registration started."
        try {
            if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank() || address.isBlank() ) {
                Log.e("UsersViewModel", "All fields must be filled out.")
                return "All fields must be filled out."
            }

            if (password.length < 6) {
                return "Password must be at least 6 characters long."
            }

            var sanitizedEmail = email.replace(" ", "")
            // Sanitize data for username
            if (!sanitizedEmail.contains("@")) {
                return "Invalid email address"
            }

            val username = createUsername(sanitizedEmail)
            val newUser = hashMapOf(
                "firstname" to firstname,
                "lastname" to lastname,
                "email" to sanitizedEmail,
                "address" to address,
                "username" to username
            )

            val userRef = db.collection("users").document(username)

            db.runTransaction { transaction ->
                var snapshot = transaction.get(userRef)

                if (!snapshot.exists()) {
                    Log.d("UsersViewModel", "User does not exist, create entry")
                    db.collection("users").document(username).set(newUser)
                    returnCode = "User created successfully"
                } else {
                    Log.d("UsersViewModel", "User exists")
                    returnCode = "User already exists"
                }
            }.addOnSuccessListener {
                Log.d("UsersViewModel", "Transaction success.")
            }.addOnFailureListener { e ->
                Log.w("UsersViewModel", "Transaction error." + e.message.toString())
            }.await()
        }catch (e: Exception)  {
            Log.w("UsersViewModel", "Fail to write user to doc.${e.message}")
            returnCode = "An error occurred during registration."
        }
        return returnCode
    }

    suspend fun registerUserToAuthDatabase(email: String, password: String): String
    {
        var returnCode = "Registration step 2 completed."

        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.d("UsersViewModel", "User added to authorization database")
            }.addOnFailureListener {
                Log.w("UsersViewModel", "Failed to add user to authorization database")
            }.await()
        } catch (e: Exception) {
            Log.w("UsersViewModel", "Failed to register user: " + e.message.toString())
            return e.message.toString()
        }

        return returnCode
    }

    suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        address: String,
        username: String
    ): String {
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank() || address.isBlank() ) {
            Log.e("UsersViewModel", "All fields must be filled out.")
            return "All fields must be filled out."
        }

        var status = "UNKNOWN ERROR"

        try {
            // Sanitize email address by removing possible spaces in middle of string
            var sanitizedEmail = email.replace(" ", "")

            val newUser = hashMapOf(
                "firstname" to firstname,
                "lastname" to lastname,
                "email" to sanitizedEmail,
                "address" to address,
                //add username
                "username" to username
            )
            // Sanitize data for username
            if (!sanitizedEmail.contains("@")) {
                throw Exception("Invalid email address")
            }

            // lowercase email address
            var username = sanitizedEmail.lowercase()
            // remove TLD from email
            username = username.substring(0, (username.lastIndexOf('.')))
            // remove . from email
            username = username.replace(".", "")
            // remove @ from email
            username = username.replace("@", "")

            newUser["username"] = username

            // Add user to Firestore (Replaces existing one..)
            runBlocking {
                db.collection("users").document(username).set(newUser).await()
            }
            status = "OK"
        } catch (e: Exception) {
            Log.e("UsersViewModel", "Failed to register user", e)
            status = e.message.toString() // Return false on failure
        }
        if (status.equals("OK")) {
            try {
                if (status.equals("OK")) {
                    status = "CREATE_USER_AUTH"
                }
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Failed to register user", e)
                status = e.message.toString() // Return false on failure
            }
        }
        return status;
    }
}


