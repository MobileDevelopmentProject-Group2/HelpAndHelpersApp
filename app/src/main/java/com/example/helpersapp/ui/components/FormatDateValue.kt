package com.example.helpersapp.ui.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateValue(date: String): String {
    Log.d("FormatDateValue", "Date: $date")
    val timeZone = TimeZone.getDefault()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    dateFormat.timeZone = timeZone
    val formattedDateTime = dateFormat.format(Date.from(OffsetDateTime.parse(date).toInstant()))
    Log.d("FormatDateValue", "Formatted date: $formattedDateTime")

    return formattedDateTime
}