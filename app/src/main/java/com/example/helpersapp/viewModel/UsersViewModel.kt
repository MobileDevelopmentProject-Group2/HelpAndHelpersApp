package com.example.helpersapp.viewModel

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helpersapp.model.HelpNeeded
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.collections.hashMapOf as hashMapOf
import com.example.helpersapp.model.User
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class UsersViewModel: ViewModel()  {
    //get the firebase auth
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    //get user data from firebase to userdetail screen, testing
    // Why we store these as local variables when no need
    //val userEmail = Firebase.auth.currentUser?.email
    //val userName = createUserIdFromEmail(userEmail.toString())

    private val _userDetails = MutableStateFlow(User())
    val userDetails: StateFlow<User> = _userDetails.asStateFlow()

    private fun createUserIdFromEmail(email: String): String {
        // Parameter sanity check
        if(email.isNullOrEmpty() || email.isBlank() ||
                !email.contains('@') || !email.contains('.')) {
            Log.e("UsersViewModel", "Invalid email: $email")
            return "";
        }

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
    fun getUserDetails () {
        viewModelScope.launch {
            try {
                val userEmail = Firebase.auth.currentUser?.email
                val userName = createUserIdFromEmail(userEmail.toString())

                // Sanity check
                if (userName.isNullOrEmpty()) {
                    throw Exception("No current user")
                }

                userEmail?.let {
                    val user = db.collection("users")
                        .document(userName)
                        .get()
                        .addOnSuccessListener { user ->
                            _userDetails.value = user.toObject(User::class.java)!!
                            Log.d("UsersViewModel", "User details retrieved successfully: ${_userDetails.value}")
                        }
                        .addOnFailureListener { e ->
                            Log.e("UsersViewModel", "Failed to get user details", e)
                        }
                    }
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Failed to get user details", e)
            }
        }
    }

    suspend fun registerUserToDocumentStore(firstname: String, lastname: String, email: String, password: String, address: String): String
    {
        var returnCode = "Registration started."

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

        val username = createUserIdFromEmail(sanitizedEmail)
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
            var userCreatedToDocumentDatabase = false;

            // Add user to Firestore (Replaces existing one..)
            runBlocking {
                db.collection("users").document(username).set(newUser).await()
            }

/*            runBlocking {
                db.collection("users").document(username).set(newUser).addOnSuccessListener {
                    Log.d(
                        "UsersViewModel",
                        "User added to Firestore, proceed creating user in authorization database"
                    )
                    userCreatedToDocumentDatabase = true;
                }.addOnFailureListener {
                    Log.e("UsersViewModel", "Failed to add user to Firestore", it)
                }.await()
            }*/
/*
            if (userCreatedToDocumentDatabase) {
                Log.d("UsersViewModel", "User created successfully")

                Firebase.auth.createUserWithEmailAndPassword(sanitizedEmail, password).addOnSuccessListener {
                    Log.d("UsersViewModel", "User added to authorization database")
                }.addOnFailureListener {
                    Log.e("UsersViewModel", "Failed to add user to authorization database", it)
                    throw Exception("Failed to add user to authorization database")
                }.await()
            } else {
                Log.e("UsersViewModel", "Failed to create user")
            }
*/
            status = "OK" // Return true on success
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


