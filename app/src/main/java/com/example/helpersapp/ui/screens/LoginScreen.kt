package com.example.helpersapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun LoginScreen(
    navController: NavController,
    //usersViewModel: ViewModel,
    loginViewModel: LoginViewModel
) {
    val localFocusManager = LocalFocusManager.current
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    //new code
    //val loginSuccess by loginViewModel.loginSuccess.observeAsState()
    var errorMessage by remember { mutableStateOf<String>("") }
    val currentUser = Firebase.auth.currentUser
    val userLoggedIn = (currentUser != null)

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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Login")
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

