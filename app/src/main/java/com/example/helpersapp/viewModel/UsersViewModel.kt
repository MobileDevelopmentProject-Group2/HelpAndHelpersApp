package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.collections.hashMapOf as hashMapOf


class UsersViewModel: ViewModel()  {
    private val db = FirebaseFirestore.getInstance()

    suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        address: String,
        username: String
    ): Boolean {
        return try {
            val newUser = hashMapOf(
                "firstname" to firstname,
                "lastname" to lastname,
                "email" to email,
                "address" to address,
                //add username
                "username" to username
            )
            val username = email.replace(".", ",")
            db.collection("users").add(newUser).await()
            true // Return true on success
        } catch (e: Exception) {
            Log.e("UsersViewModel", "Failed to register user", e)
            false // Return false on failure
        }
    }
}
/*
0327 old code
    private val db = FirebaseFirestore.getInstance()
    private val _statusMessage = MutableLiveData<String?>()
    val statusMessage: MutableLiveData<String?> = _statusMessage

    //val statusMessage: LiveData<String?>
      //  get() = _statusMessage
    private val _navigateToLogin = MutableLiveData<Boolean>(false)
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin
    //private val _navigateToLogin = MutableStateFlow(false)
    //val navigateToLogin = _navigateToLogin.asStateFlow()
       viewModelScope.launch {
           try {
               val newUser = hashMapOf(
                   "firstname" to firstname,
                   "lastname" to lastname,
                   "email" to email,
                   //"password" to password,
                   "address" to address
               )
               db.collection("users").add(newUser).await()
               true // Return true on success
           } catch (e:Exception) {
               Log.e("UsersViewModel", "Failed to register user", e)
               false // Return false on failure

           }
       } */