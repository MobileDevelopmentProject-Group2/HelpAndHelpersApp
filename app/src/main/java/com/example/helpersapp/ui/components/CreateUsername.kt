package com.example.helpersapp.ui.components

import android.util.Log

fun createUsername(email: String): String {
    try {
        //get the first part of username until @
        val stringUntilAtSign = email.substringBefore('@')

        //if first part has dot, remove it
        val dotRemoved = stringUntilAtSign.replace(".", "")

        //get the email provider between @ and .
        val emailProviderRemoved = email.substringAfter('@').substringBefore('.')

        //combine the username and emailProviderRemoved
        val username = "$dotRemoved$emailProviderRemoved"

        return username
    } catch (e: Exception) {
        Log.d("CreateUsername", "Error creating username: ${e.message}")
        return email
    }

}
