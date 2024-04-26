package com.example.helpersapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun HelperRating(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier
            .selectableGroup()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0xFF000000)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .padding(8.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i)
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Composable
fun ShowHelperRating(rating: Float) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp
    var stars by rememberSaveable { mutableIntStateOf(0) }

    when (rating) {
        in 1.00..1.49 -> stars = 1
        in 1.5..2.49 -> stars = 2
        in 2.5..3.49 -> stars = 3
        in 3.5..4.49 -> stars = 4
        in 4.5..5.00 -> stars = 5
    }
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in 1..5) {
            val isSelected = i <= stars
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0xFF000000)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .padding(8.dp)
                    .width(starSize)
                    .height(starSize)
            )
            if (i < 5) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}