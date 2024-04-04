package com.example.helpersapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginViewModel : ViewModel() {
    val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    //get user login data
    //val user : FirebaseUser?
    //get() = Firebase.auth.currentUser

    fun loginUser(email: String, password: String, onResult:  (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (authResult.user != null) {
                    onResult(true, null)
                } else {
                    onResult(false, "Email or password is incorrect. Please try again.")
                }

            }catch (e:Exception) {
                onResult(false, e.message ?: "An error occurred")
            }
        }
    }
    fun logoutUser() {
        firebaseAuth.signOut()
    }
}


