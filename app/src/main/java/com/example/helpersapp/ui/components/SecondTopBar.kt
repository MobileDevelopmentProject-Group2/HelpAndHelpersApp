package com.example.helpersapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondTopBar(navController: NavController) {
    val user = Firebase.auth.currentUser
    val loginViewModel = LoginViewModel()
    val helpViewModel = HelpViewModel()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "CareConnect",
                fontSize = 22.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (user != null) {
                Column {
                    IconButton(
                        onClick = {
                            helpViewModel.emptyHelpList()
                            helpViewModel.emptyNewHelpNeeded()
                            helpViewModel.setCategory("")
                            loginViewModel.logoutUser()
                            navController.navigate("home")

                        }
                    ) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "My Data")
                }
                    Text(
                        text = "Logout",
                        modifier = Modifier.offset(y = (-10).dp),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                    )
                }
            } else {
                Column {
                    IconButton(
                        onClick = { navController.navigate("login") }
                    ) {
                        Icon(Icons.Filled.Lock, contentDescription = "Login")
                    }
                    Text(
                        text = "Login",
                        modifier = Modifier.offset(y = (-10).dp),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}