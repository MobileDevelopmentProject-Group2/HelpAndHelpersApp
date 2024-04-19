package com.example.helpersapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helpersapp.BuildConfig.MAP_API_KEY
import com.example.helpersapp.model.Geocode
import com.example.helpersapp.model.GeocodeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val geocode: Geocode): ViewModel() {
    private val apiKey = MAP_API_KEY

    private val _geocodeResult = MutableStateFlow<GeocodeResponse?>(null)
    var geocodeResult: StateFlow<GeocodeResponse?> = _geocodeResult.asStateFlow()

    fun getCoordinates(postalCode: String) {
        viewModelScope.launch {
            try {
                val response = geocode.getGeocode("$postalCode,Oulu,FI", apiKey)

                if (response.status == "OK") {
                    _geocodeResult.value = response
                    Log.d("MapViewModel", "Geocode response: ${response.results}")
                } else {
                    Log.d("MapViewModel", "Error fetching code: ${response.status}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("MapViewModel", "Error fetching code: ${e.message}")
            }

        }
    }
}