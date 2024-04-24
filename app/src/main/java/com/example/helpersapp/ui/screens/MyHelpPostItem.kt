package com.example.helpersapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helpersapp.model.HelpNeeded
import com.example.helpersapp.ui.components.formatDateValue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyHelpPostItem (
    helpNeeded: HelpNeeded
) {
    val priceRangeText = "${helpNeeded.priceRange.start.toInt()} € - ${helpNeeded.priceRange.endInclusive.toInt()} €"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(6.dp),
        //elevation = 4.dp

    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){

            Text(
                text = "Date: ${helpNeeded.date}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = helpNeeded.workDetails,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = priceRangeText,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Posted Date: ${formatDateValue(helpNeeded.requestPostDate)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Contact email:  ${(helpNeeded.userEmail)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

