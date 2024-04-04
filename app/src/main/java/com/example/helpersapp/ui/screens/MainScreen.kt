package com.example.helpersapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.helpersapp.viewModel.HelperViewModel

@Composable
fun MainScreen(
    navController: NavController,
    usersViewModel: ViewModel,
    helpNeededViewModel: ViewModel,
    helperViewModel: HelperViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Main/home Screen"
        )
        Button(onClick = { navController.navigate("home")}) {
            Text(text = "to landing screen")
        }
        Button(onClick = { navController.navigate("login") }) {
            Text(text = "To Login Screen")
        }
        Button(onClick = { navController.navigate("register") }) {
            Text(text = "To Register Screen")
        }
        Button(onClick = { navController.navigate("addHelp") }) {
            Text(text = "To Add New Help Screen")
        }
        Button(onClick = { navController.navigate("helpDetails") }) {
            Text(text = "To Help Details Screen")
        }
        Button(onClick = { navController.navigate("postHelper") }) {
            Text(text = "To Post New Helper Details Screen")
        }
        Button(onClick = { navController.navigate("profile") }) {
            Text(text = "profile")
        }
    }
}