package com.example.helpersapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapActivity(areaName: String, latitude: Double, longitude: Double) {
    val area = com.google.android.gms.maps.model.LatLng(latitude, longitude)
    Log.d("MapActivity", "Area: $area, Name: $areaName, Latitude: $latitude, Longitude: $longitude")

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(area, 13f)
    }
    val uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties
    ) {
        Marker(
            state = MarkerState(position = area),
            title = areaName,
            snippet = "Approximate location",
        )
        Circle(
            center = area,
            radius = 800.0,
            fillColor = Color(0x330000FF),
            strokeColor = Color(0x660000FF),
            strokeWidth = 5f

        )
    }
}
