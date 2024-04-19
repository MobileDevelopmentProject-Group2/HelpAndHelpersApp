package com.example.helpersapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helpersapp.model.Geocode

class MapViewModelFactory(
    private val geocode: Geocode
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            return MapViewModel(geocode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}