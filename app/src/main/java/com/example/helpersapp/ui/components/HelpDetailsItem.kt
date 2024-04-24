package com.example.helpersapp.ui.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.helpersapp.model.HelpNeeded
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HelpDetailsItem(helpDetails: HelpNeeded) {
    val dateTimeNow = SimpleDateFormat("yyyy-MM-dd HH:mm").apply {
        timeZone = TimeZone.getDefault()
    }.format(Date(System.currentTimeMillis()))
    val properties = arrayOf(
        "Category" to helpDetails.category,
        "Work Details" to helpDetails.workDetails,
        "Date" to helpDetails.date,
        "Time" to helpDetails.time,
        "Price Range" to helpDetails.priceRange.toString(),
        "Postal Code" to helpDetails.postalCode,
        if (helpDetails.requestPostDate.isNotEmpty()) "Request posted" to formatDateValue(helpDetails.requestPostDate) else "Request posted" to dateTimeNow,

    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        properties.forEach { (label, value) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "${label}: ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = if (label != "Price Range") value else helpDetails.priceRange.endInclusive.toInt().toString() + " € - " + helpDetails.priceRange.start.toInt().toString() + " €",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}
