package com.example.helpersapp.ui.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.createUsername
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ShowBottomImage()
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            Text(
                text = "Helper Detail",
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            ShowStorageImage()

            Text(text = "${user.firstname} ${user.lastname}",
                style = MaterialTheme.typography.titleLarge,)

            Spacer(modifier = Modifier.height(8.dp))

            HelperDetail(helperInfo.value)

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigateUp() },
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(52.dp)
                        .width(150.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Modify")
                }
                Button(
                    onClick = {
                        navController.navigate("main")
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(52.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}
@Composable
fun HelperDetail(helperInfo: HelperInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "About: ${helperInfo.about}")
        Text(text = "Category: ${helperInfo.category}")
        Text(text = "Details: ${helperInfo.details}")
        Text(text = "Experience: ${helperInfo.experience}")
    }
}


@Composable
fun ShowStorageImage() {
    var byteArray by remember { mutableStateOf<ByteArray?>(null) }

    var painter = rememberAsyncImagePainter(
        model = byteArray
    )
    val useremail = Firebase.auth.currentUser?.email
    val username = createUsername(useremail ?: "")

    Column() {
        Firebase.storage.reference
            .child("tomoemail/profile_picture.jpg")
            //.child("{$username}/profile_picture.jpg")
            .getBytes(1024*1024)
            .addOnSuccessListener {
                byteArray = it
            }
            .addOnFailureListener {
                Log.e("***", it.message.toString())
            }
        Image(
            painter = painter,
            contentDescription = "images",
            modifier = Modifier.fillMaxSize()
        )
    }
}