package com.example.helpersapp.viewModel

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.helpersapp.ui.components.createUsername
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun rememberFirebaseAuthLauncher(
    navController: NavController,
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val loginViewModel = LoginViewModel()
    val helpViewModel = HelpViewModel()

    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            val email = account.email.toString()
            val userName = createUsername(email)
            scope.launch {
                val userSnapshot = Firebase.firestore.collection("users").document(userName).get().await()
                if (userSnapshot.exists()) {
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    val userId = authResult.user?.uid.toString()
                    onAuthComplete(authResult)
                    loginViewModel.setUserId(userId)
                    loginViewModel.getUserDetails(userName)
                    helpViewModel.getAllHelpRequests()
                    Log.d("GoogleSignIn", "User logged in successfully: ${userId}")
                    navController.navigate("main")
                } else {
                    navController.navigate("register")
                    val errorMessage = "User $userName does not exist"
                    Log.d("GoogleSignIn", "Error in SignIn: $errorMessage")
                    val apiException = ApiException(Status.RESULT_INTERNAL_ERROR)
                    onAuthError(apiException)
                }
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}
