package com.example.helpersapp.ui.components

import android.util.Log
import com.google.firebase.Timestamp
import okhttp3.MediaType.Companion.toMediaType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatDateValue(date: String): String {
    Log.d("FormatDateValue", "Date: $date")
    //If date is a TimeStamp, convert it to a Date object
    if (date.contains("-")) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateValue = dateFormat.parse(date)
        val formattedDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(dateValue)
        Log.d("FormatDateValue", "Formatted date: $formattedDateTime")
        return formattedDateTime
    }
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    //dateFormat.timeZone = TimeZone.getDefault()
    val dateValue = date.toLongOrNull()?.let { Date(it) }
    val formattedDateTime = dateValue?.let { dateFormat.format(it) } ?: "N/A"
    Log.d("FormatDateValue", "Formatted date: $formattedDateTime")

    return formattedDateTime
}
/*
* Copy code
import java.text.SimpleDateFormat
import java.util.*

fun parseTimestampToLocalTime(timestamp: com.google.firebase.Timestamp): String {
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