package com.example.helpersapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.helpersapp.R

@Composable
fun ShowBottomImage() {
    Image(
        painter = painterResource(id = R.drawable.bottom_background_with_logo),
        contentDescription = "Background image of the bottom bar",
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
    )
}