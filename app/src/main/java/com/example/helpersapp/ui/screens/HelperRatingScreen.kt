package com.example.helpersapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.model.User
import com.example.helpersapp.ui.components.HelperRating

@Composable
fun HelperRatingScreen(
    helperInfo: HelperInfo,
    user: User,
    byteArray: ByteArray
){
    var rating by rememberSaveable { mutableStateOf(1f) }

    Column {
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
        Text(text = "give your rating to this helper")
        Spacer(modifier = Modifier.height(24.dp))
        HelperRating(
            maxStars = 5,
            rating = rating,
            onRatingChanged = {
                rating = it
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Rating: $rating", modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /*TODO*/ }) {
            Text("Save Rating")

        }
    }
}