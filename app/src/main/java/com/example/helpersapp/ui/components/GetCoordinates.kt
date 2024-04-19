package com.example.helpersapp.ui.components

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

data class Location(val areaName: String, val latitude: Double, val longitude: Double)

fun getLocationByPostalCode(context: Context, postalCode: String): Location? {
    Log.d("Coordinates", "Fetching location for postal code: $postalCode")
    try {
        context.assets.open("postalCodes_to_coordinates.csv").use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String?
                // Skip the header line if exists
                while (reader.readLine().also { line = it } != null) {
                    val tokens = line!!.split(";")
                    if (tokens.size >= 4 && tokens[0] == postalCode) {
                        val areaName = tokens[1]
                        val latitude = tokens[2].toDouble()
                        val longitude = tokens[3].toDouble()
                        Log.d("Coordinates", "Location found: $areaName, $longitude, $latitude")
                        return Location(areaName, latitude, longitude)
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("Coordinates", "Error fetching code: ${e.message}")
    }
    return null
}