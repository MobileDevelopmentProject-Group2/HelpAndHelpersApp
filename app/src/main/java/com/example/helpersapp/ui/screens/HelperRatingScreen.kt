package com.example.helpersapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.helpersapp.R
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.model.User
import com.example.helpersapp.ui.components.HelperRating
import com.example.helpersapp.ui.components.SecondTopBar
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelperViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun HelperRatingScreen(
    navController: NavController,
    helperViewModel: HelperViewModel,
    fullName: String,
    username: String,
){
    var byteArray by rememberSaveable { mutableStateOf<ByteArray?>(null) }
    var rating by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect("gs://careconnect-65e41.appspot.com") {
        Firebase.storage.reference
            .child("$username/profile_picture.jpg")
            .getBytes(1024 * 1024)
            .addOnSuccessListener { fetchedBytes ->
                byteArray = fetchedBytes
                Log.d("HelperDetails", "ByteArray: $fetchedBytes") }
            .addOnFailureListener { exception ->
                Log.e("***", "Error fetching image: ${exception.message}")
                byteArray = null
            }
    }
    Box {
        ShowBottomImage()
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                text = fullName,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Give your rating to this helper",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            HelperRating(
                maxStars = 5,
                rating = rating,
                onRatingChanged = {
                    rating = it
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your rating: ${rating.toInt()} stars",
                modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    helperViewModel.saveHelperRating(rating, username)
                    navController.navigateUp()
                },
                modifier = Modifier
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                Text("Save Rating")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    helperViewModel.setHelperRating()
                    navController.navigateUp()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .padding(top = 80.dp)
                    .height(52.dp)
                    .width(150.dp)
                    .offset(x = (-100).dp)

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = stringResource(R.string.back_))
            }
        }
    }
}