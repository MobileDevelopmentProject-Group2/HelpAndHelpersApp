package com.example.helpersapp.ui.components

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateValue(date: String): String {
    Log.d("FormatDateValue", "Date: $date")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val dateValue = date.toLongOrNull()?.let { Date(it) }
    val formattedDateTime = dateValue?.let { dateFormat.format(it) } ?: "N/A"
    Log.d("FormatDateValue", "Formatted date: $formattedDateTime")

    return formattedDateTime
}
/*

import java.text.SimpleDateFormat
import java.util.*

fun formatDateValue(timestamp: com.google.firebase.Timestamp): String {
    // Convert the Firebase Timestamp to a Java Date
    val date = timestamp.toDate()

    // Get the user's default time zone
    val timeZone = TimeZone.getDefault()

    // Create a SimpleDateFormat object with the desired format and time zone
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    dateFormat.timeZone = timeZone

    // Format the Date object to a String in the local time zone
    return dateFormat.format(date)
}
* */