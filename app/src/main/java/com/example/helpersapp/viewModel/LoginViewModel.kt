package com.example.helpersapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginViewModel : ViewModel() {
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    //val loginSuccess = MutableLiveData<Boolean>()
    //val errorMessage = MutableLiveData<String>()
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
    }



