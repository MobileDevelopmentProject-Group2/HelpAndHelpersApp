package com.example.helpersapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.SecondTopBar
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@Composable
fun HelperListingScreen(
    navController: NavController,
    helperViewModel: HelperViewModel,
    loginViewModel: LoginViewModel,
) {
    var nannies by remember { mutableStateOf(emptyList<HelperInfo>()) }

    LaunchedEffect(Unit) {
        helperViewModel.getNannies(
            onSuccess = { nannyList ->
                nannies = nannyList
            },
            onFailure = { e ->
                Log.e("***", "Error fetching nanny list: ${e.message}")
            }
        )
    }
    Scaffold(
        topBar = {
            SecondTopBar(navController)
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "All nannies",
                    modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    )
        Box(modifier = Modifier.fillMaxSize()) {
            ShowBottomImage()
            Column {
                Spacer(modifier = Modifier.height(140.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(nannies) { nanny ->
                        NannyCard(nanny = nanny, loginViewModel = loginViewModel){
                            val currentUser = loginViewModel.getUsername()
                            val clickedUsername = nanny.username
                            val navigateToDetailsScreen = {
                                if (currentUser == clickedUsername) {
                                    navController.navigate("helperDetailsScreen")
                                } else {
                                    navController.navigate("helperDetailsScreenAnotherUsername")
                                }
                                helperViewModel.saveClickedUsername(clickedUsername)
                                Log.e("***", " ${clickedUsername}")
                            }
                                navigateToDetailsScreen()
                        }
                    }
                }
            }
        }
    }

@Composable
fun NannyCard(nanny: HelperInfo, loginViewModel: LoginViewModel,onClick: () -> Unit) {
    Log.d("from NannyCard",nanny.username.toString()+"/profile_picture.jpg")
    var byteArray by remember {
        mutableStateOf<ByteArray?>(null)
    }
    var painter = rememberAsyncImagePainter(model = byteArray)
    val imagePath = nanny.username.toString()+"/profile_picture.jpg"

    Log.d("from NannyCard2",nanny.username)

    LaunchedEffect(nanny) {
        getNannyProfilePictureByteArray(nanny, loginViewModel) { fetchedBytes ->
            byteArray = fetchedBytes
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                if (byteArray != null) {
                    Image(
                        painter = painter,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "No Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(shape = CircleShape)
                    )
                }

                Text(
                    text = nanny.fullName.ifEmpty { "Unknown" },
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

fun getNannyProfilePictureByteArray(nanny: HelperInfo, loginViewModel: LoginViewModel, onFetchComplete: (ByteArray) -> Unit) {
    val username = nanny.username
    val url = "gs://careconnect-65e41.appspot.com/"
    val imagePath = "$username/profile_picture.jpg"
    val storageReference = Firebase.storage.reference.child(imagePath)

    storageReference.getBytes(1024 * 1024)
        .addOnSuccessListener { fetchedBytes ->
            Log.d("12t", "Nanny profile picture successfully fetched. Image byte array size: ${fetchedBytes.size}. ${username} Fetching image from URL: $url")
            onFetchComplete(fetchedBytes)
        }
        .addOnFailureListener { exception ->
            Log.e("11t", "Error fetching nanny profile picture: ${exception.message}${username}")
        }
}