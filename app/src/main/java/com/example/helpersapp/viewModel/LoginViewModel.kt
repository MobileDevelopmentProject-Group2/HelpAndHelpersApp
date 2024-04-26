package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import com.example.helpersapp.model.User
import com.example.helpersapp.ui.components.createUsername
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var _userID = MutableStateFlow<String>("")
    var userID: StateFlow<String> = _userID.asStateFlow()

    private val _userDetails = MutableStateFlow(User())
    val userDetails: StateFlow<User> = _userDetails.asStateFlow()

    fun setUserId(userId: String) {
        _userID.value = userId
        Log.d("LoginViewModel", "User ID set: ${_userID.value}")
    }

    fun loginUser(email: String, password: String, onResult:  (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (authResult.user != null) {
                    onResult(true, null)
                    _userID.value = authResult.user!!.uid
                    Log.d("LoginViewModel", "User logged in successfully: ${_userID.value}")
                    var userName = createUsername(email)
                    getUserDetails(userName)

                } else {
                    onResult(false, "Email or password is incorrect. Please try again.: ${authResult.user})")
                }

            }catch (e:Exception) {
                Log.d("LoginViewModel2", "Failed to log in user", e)
                onResult(false, e.message ?: "An error occurred")
            }
        }
    }
    fun getUserDetails(userName: String) {
        viewModelScope.launch {
            try {
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
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Failed to get user details", e)
            }
        }
    }
    fun getUsername(): String {
        return _userDetails.value.username
    }
    fun deleteUser() {
        val username = firebaseAuth.currentUser?.email?.let { createUsername(it) }
        viewModelScope.launch {
            try {
                val user = firebaseAuth.currentUser
                user?.delete()
                    ?.addOnSuccessListener {
                        if (username != null) {
                            deleteUserData(username)
                        }
                        Log.d("LoginViewModel", "User deleted successfully")
                    }
                    ?.addOnFailureListener { e ->
                        Log.e("LoginViewModel", "Failed to delete user", e)
                    }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Failed to delete user", e)
            }
        }
    }
    private fun deleteUserData(username: String) {
        viewModelScope.launch {
            try {
                db.collection("users")
                    .document(username)
                    .delete()
                    .addOnSuccessListener {
                        _userDetails.value = User()
                        _userID.value = ""
                        firebaseAuth.signOut()
                        Log.d("LoginViewModel", "User data deleted successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("LoginViewModel", "Failed to delete user data", e)
                    }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Failed to delete user data", e)
            }
        }
    }
    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    onSuccess()
                    Log.d("LoginViewModel", "Password reset email sent successfully")
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                    Log.e("LoginViewModel", "Failed to send password reset email", e)
                }
        }
    }
    fun logoutUser() {
        viewModelScope.launch {
            try {
                _userID.value = ""
                _userDetails.value = User()
                firebaseAuth.signOut()
                Firebase.auth.signOut()
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Log.e("LoginViewModel", "Failed to log out user", e)
            }
        }
    }
}

