package com.example.helpersapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.viewModel.LoginViewModel
import com.example.helpersapp.viewModel.UsersViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.example.helpersapp.ui.components.ShowBottomImage as ShowBottomImage


@Composable
fun UserProfileScreen(navController: NavController, usersViewModel: UsersViewModel) {
    val context = LocalContext.current
    val user = Firebase.auth.currentUser
    LaunchedEffect(key1 = user) {
        user?.uid?.let {
            //usersViewModel.fetchUserDetails(it)
        }
    }
    val userDetails = usersViewModel.userDetails.collectAsState().value
    if (userDetails == null) {
        Text(text = "Please log in to view your profile")
    }else {
        ShowBottomImage()
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "User Profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
            )
            userDetails["firstname"]?.let { firstname ->
                userDetails["lastname"]?.let { lastname ->
                    Text(text = "Name: $firstname $lastname")
                }
            }
            //Text(text = "Email: ${userDetails.email}", style = MaterialTheme.typography.headlineMedium)
            //Spacer(modifier = Modifier.height(16.dp))
            //Text(text = "Address: ${userDetails.address}", style = MaterialTheme.typography.headlineMedium)
            //Spacer(modifier = Modifier.height(16.dp))
            //Text(text = "Username: ${userDetails.username}", style = MaterialTheme.typography.headlineMedium)
            //Spacer(modifier = Modifier.height(32.dp))
        }
    }

            /*
            currentUser?.let {
                Text(text = "Email: $email}", style = MaterialTheme.typography.bodyMedium),
                Text(text = "First Name: ${it.firstname}", style = MaterialTheme.typography.bodyMedium),

            }*/


















        }


