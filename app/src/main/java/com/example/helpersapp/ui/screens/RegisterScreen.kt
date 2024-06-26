package com.example.helpersapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.viewModel.UsersViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, usersViewModel: UsersViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    // Add this to handle keyboard actions like dismissing the keyboard
    val localFocusManager = LocalFocusManager.current
    var firstname by remember{ mutableStateOf("")}
    var lastname by remember{ mutableStateOf("")}
    var email by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    var address by remember{ mutableStateOf("")}
    var username by remember { mutableStateOf("") }

    //notice and message
    var showRegistrationResult by remember { mutableStateOf(false) }
    var registrationSuccessful by remember { mutableStateOf("") }


    Column(
        // add padding
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.mipmap.top_background) ,
            contentDescription =null,
            modifier = Modifier
                .fillMaxWidth()
            )

        Text(
            text = "Register",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displayMedium
        )

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
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() }),
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = address,
            onValueChange = {address = it},
            label = { Text(text = "Address") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() })
        )

        Button(
            onClick = {
                var returnMsg = "";
                coroutineScope.launch {
                    // Registration step 1 - Register user to document store
                    var returnMessage = usersViewModel.registerUserToDocumentStore(firstname.trim(), lastname.trim(), email.trim(), password.trim(), address.trim())

                    if (returnMessage.equals("User already exists") || returnMessage.equals("User created successfully")) {
                        // Registration step 2 - Register user to auth database
                        returnMessage = usersViewModel.registerUserToAuthDatabase(email.trim(), password.trim())

                        if (returnMessage.equals("Registration step 2 completed.")) {
                            Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, returnMessage, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // In case 1 step fails, show some meaningful error message
                        Toast.makeText(context, returnMessage, Toast.LENGTH_LONG).show()
                        returnMsg = "Registration failed: " + returnMessage
                    }
                }


            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Register")
        }


        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already a user? Login", style = MaterialTheme.typography.bodyMedium)
        }
        //new code for paivacy policy
        TextButton(onClick = { navController.navigate("privacy") }) {
            Text("By Register to a member, you agree our privacy policy", style = MaterialTheme.typography.bodyMedium)
        }

        Button(onClick = { navController.navigate("main")}) {
            Text(text = "Home")
        }
    }
}
