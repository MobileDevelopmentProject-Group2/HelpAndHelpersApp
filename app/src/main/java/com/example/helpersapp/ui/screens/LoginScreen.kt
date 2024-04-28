package com.example.helpersapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.createUsername

import com.example.helpersapp.viewModel.LoginViewModel
import com.example.helpersapp.viewModel.rememberFirebaseAuthLauncher
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@SuppressLint("RestrictedApi")
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val localFocusManager = LocalFocusManager.current
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String>("") }
    var currentUser = Firebase.auth.currentUser
    val userLoggedIn = (currentUser != null)
    var showEmailNotice by remember { mutableStateOf(false) }

    // for Google sign in:
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        navController,
        onAuthComplete = { result -> user = result.user },
        onAuthError = { user = null }
    )
    val token = stringResource(id = R.string.web_client_id)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Image(
            painter = painterResource(id = R.mipmap.top_background) ,
            contentDescription =null,
            modifier = Modifier
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.mipmap.logo) ,
            contentDescription =null,
            modifier = Modifier
                .height(180.dp)
        )
        Text(
            text = "Login" ,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displayMedium
        )
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() })

        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() }),
            visualTransformation = PasswordVisualTransformation()
        )
        // Display error message if not null or empty
        if(!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(
            onClick = {
                loginViewModel.sendPasswordResetEmail(
                    email,
                    onSuccess = {
                        showEmailNotice = true
                    },
                    onFailure = {
                        Toast.makeText(context, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        ) {
            Text(
                text = "Forgot password?\nPlease type in your email and click here to reset.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        if (showEmailNotice) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Password reset email sent!",
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Magenta
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                // Handle the sign-up logic here
                localFocusManager.clearFocus()
                loginViewModel.loginUser(email, password){
                        success, error ->
                    if(success){
                        navController.navigate("main")
                    }else{
                        errorMessage = "Login failed: $error"
                    }
                }
            },
            modifier = Modifier
                .padding(10.dp)
                .width(280.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = "or",
            style = MaterialTheme.typography.bodyLarge
        )
        if (!userLoggedIn) {
            Button(
                onClick = {
                    try {
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Failed to sign in with Google", Toast.LENGTH_SHORT).show()
                    }

                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .width(280.dp)
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.googlelogo ),
                        contentDescription = "GoogleLogo",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                    )
                    Text(
                        text = "Sign in with Google",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Not a user? Register here",
                style = MaterialTheme.typography.bodyLarge)
        }

        //if user lgoin, then link to the home
        Button(enabled = userLoggedIn, onClick = { navController.navigate("main")}) {
            Text(text = "Home")
        }
        SodaLogo()
    }
}

@Composable
fun SodaLogo() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {

//this iconbutton looks not right
        IconButton(onClick = { /* TODO: Add action for Facebook */ }) {
            Image(painter = painterResource(id = R.drawable.facebook), contentDescription = "Facebook")
        }
        IconButton(onClick = { /* TODO: Add action for Facebook */ }) {
            Image(painter = painterResource(id = R.mipmap.google), contentDescription = "Facebook")
        }
        IconButton(onClick = { /* TODO: Add action for Facebook */ }) {
            Image(painter = painterResource(id = R.mipmap.twitter), contentDescription = "Facebook")
        }
    }
}

