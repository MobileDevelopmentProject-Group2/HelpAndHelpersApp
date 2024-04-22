package com.example.helpersapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.HelperDetail
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun HelperDetailsScreen(
    navController: NavController,
    helperViewModel: HelperViewModel,
    loginViewModel: LoginViewModel,
) {
    val helperInfo = remember { mutableStateOf(HelperInfo("", "", "", "", "","")) }
    val user by loginViewModel.userDetails.collectAsState()

    LaunchedEffect(Unit) {
        helperViewModel.getHelperDetails(
            username = loginViewModel.getUsername(),
            onSuccess = { info ->
                helperInfo.value = info
            },
            onFailure = { e ->
                // Handle failure
                Log.e("***", "Error fetching helper details: ${e.message}")
            }
        )
    }

    val username = loginViewModel.getUsername()
    val url = "gs://careconnect-65e41.appspot.com/"
    var byteArray by remember { mutableStateOf<ByteArray?>(null) }

    LaunchedEffect(url) {
        Firebase.storage.reference
            .child("$username/profile_picture.jpg")
            .getBytes(1024 * 1024)
            .addOnSuccessListener { fetchedBytes ->
                byteArray = fetchedBytes
                Log.d("***", "Image successfully fetched. Image byte array size: ${fetchedBytes.size}.Fetching image from URL: $url")
            }
            .addOnFailureListener { exception ->
                Log.e("***", "Error fetching image: ${exception.message}")
                byteArray = null
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ShowBottomImage()

        Column(
            modifier = Modifier
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            byteArray?.let {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = byteArray
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${user.firstname} ${user.lastname}",
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            HelperDetail(helperInfo.value)

            Text(
                text = "Certification list : "
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
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
                    onClick = { navController.navigate("main") },
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