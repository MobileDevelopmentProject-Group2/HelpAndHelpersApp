package com.example.helpersapp.ui.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateValue(date: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val dateValue = date?.takeIf { it.isNotBlank() }?.toLongOrNull()?.let { Date(it) }
    val formattedDateTime = dateValue?.let { dateFormat.format(it) } ?: "N/A"

    return formattedDateTime
}