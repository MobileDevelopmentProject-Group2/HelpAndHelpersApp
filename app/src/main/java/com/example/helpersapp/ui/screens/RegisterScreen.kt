package com.example.helpersapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalProvider
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.helpersapp.R
import androidx.compose.material3.Text as Text

//import sun.tools.jstat.Alignment

@Composable
fun RegisterScreen(navController: NavController, usersViewModel: ViewModel) {
    // below my code
    // Add this to handle keyboard actions like dismissing the keyboard
    val localFocusManager = LocalFocusManager.current
    var firstname by remember{ mutableStateOf(" ")}
    var lastname by remember{ mutableStateOf(" ")}
    var email by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    var address by remember{ mutableStateOf("")}
    Column(
        // add padding
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.mipmap.top_background) ,
            contentDescription =null,
            modifier = Modifier
                .fillMaxWidth()
                //.height(150.dp)
            )

        Text(
            text = "Register",
                    //below new code
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displayMedium
        )
        //Spacer(modifier = Modifier.height(200.dp))

        OutlinedTextField(
            value = firstname,
            onValueChange = {firstname = it},
            label = { Text(text = "Firstname") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() })
            //keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) })
        )
        OutlinedTextField(
            value = lastname,
            onValueChange = {lastname = it},
            label = { Text(text = "Lastname") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() })

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
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() })

        )
        OutlinedTextField(
            value = address,
            onValueChange = {address = it},
            label = { Text(text = "Address") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() })
        )
        //Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = {
                // Handle the sign-up logic here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Register")
        }
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already a user? Login", style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = { navController.navigate("main")}) {
            Text(text = "Home")
        }
    }
}