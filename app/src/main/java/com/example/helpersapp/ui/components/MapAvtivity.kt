package com.example.helpersapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.checkerframework.checker.units.qual.Area

@Composable
fun MapActivity(postalCode: String) {
    
    val oulu = com.google.android.gms.maps.model.LatLng(65.01236, 25.46816)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(oulu, 13f)
    }
    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties,
    ) {
        Marker(
            state = MarkerState(position = oulu),
            title = "Oulu",
            snippet = "Finland",
        )
        Circle(
            center = oulu,
            radius = 800.0,
            fillColor = Color(0x330000FF),
            strokeColor = Color(0x660000FF),
            strokeWidth = 5f

        )
    }
}
