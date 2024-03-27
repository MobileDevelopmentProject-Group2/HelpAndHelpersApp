package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.collections.hashMapOf as hashMapOf


class UsersViewModel: ViewModel()  {
    private val db = FirebaseFirestore.getInstance()
    private val _statusMessage = MutableLiveData<String?>()
    val statusMessage: LiveData<String?>
        get() = _statusMessage
    fun registerUser(firstname: String, lastname: String, email: String, password: String, address: String) {
        Log.d("UsersViewModel", "UsersViewModel")
        viewModelScope.launch {
            try {
                val newUser = hashMapOf(
                    "firstname" to firstname,
                    "lastname" to lastname,
                    "email" to email,
                    "password" to password,
                    "address" to address
                )
                db.collection("users").add(newUser).await()
                _statusMessage.value = "User registered successfully"
            } catch (e:Exception) {

        // Handle the exception by updating the status message
                    _statusMessage.value = "Failed to register user: ${e.message}"

            }
        }
    }
    fun clearStatusMessage() {
        _statusMessage.value = null
    }
}