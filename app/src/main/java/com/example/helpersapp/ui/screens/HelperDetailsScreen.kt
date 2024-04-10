package com.example.helpersapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel

@Composable
fun HelperDetailsScreen(
    navController: NavController,
    helperViewModel: HelperViewModel,
    loginViewModel: LoginViewModel,
    //username: String
) {
    val helperInfo = remember { mutableStateOf(HelperInfo("", "", "", "", "")) }
    val user by loginViewModel.userDetails.collectAsState()

    LaunchedEffect(Unit) {
        helperViewModel.getHelperDetails(
            username = loginViewModel.getUsername(),
            onSuccess = { info ->
                helperInfo.value = info
            },
            onFailure = { e ->
                // Handle failure
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Helper Detail",
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${user.firstname} ${user.lastname}")
        Spacer(modifier = Modifier.height(8.dp))
        HelperDetail(helperInfo.value)
    }
}

@Composable
fun HelperDetail(helperInfo: HelperInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "About: ${helperInfo.about}")
        Text(text = "Category: ${helperInfo.category}")
        Text(text = "Details: ${helperInfo.details}")
        Text(text = "Experience: ${helperInfo.experience}")
    }
}
