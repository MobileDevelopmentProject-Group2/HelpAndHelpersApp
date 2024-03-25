package com.example.helpersapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.helpersapp.R

@Composable
fun LoginScreen(navController: NavController, usersViewModel: ViewModel) {
    val localFocusManager = LocalFocusManager.current
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
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
                //.padding(top=1.dp)
                //.fillMaxWidth()

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
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Button(
            onClick = {
                // Handle the sign-up logic here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Login")
        }
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Not a user? Register here",
                style = MaterialTheme.typography.bodyLarge)
        }
        Button(onClick = { navController.navigate("home")}) {
            Text(text = "Home")
        }
        SodaLogo()
    }


}

@Composable
fun SodaLogo() {
   val socialLogos = listOf(R.mipmap.facebook, R.mipmap.google, R.mipmap.twitter)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {

//this iconbutton looks not right
        IconButton(onClick = { /* TODO: Add action for Facebook */ }) {
            Image(painter = painterResource(id = R.mipmap.facebook), contentDescription = "Facebook")
        }
        IconButton(onClick = { /* TODO: Add action for Facebook */ }) {
            Image(painter = painterResource(id = R.mipmap.google), contentDescription = "Facebook")
        }
        IconButton(onClick = { /* TODO: Add action for Facebook */ }) {
            Image(painter = painterResource(id = R.mipmap.twitter), contentDescription = "Facebook")
        }
        /*
              socialLogos.forEach { logoRes ->
            Image(
                painter = painterResource(id = logoRes),
                contentDescription = "Social media",
                modifier = Modifier.size(48.dp)
                )
        }

                IconButton(
            onClick = { /* TODO: Add action for Facebook */ })
        {
            Icon(painter = painterResource(id = R.mipmap.facebook), contentDescription = "Facebook")
        }
        * */
    }
}