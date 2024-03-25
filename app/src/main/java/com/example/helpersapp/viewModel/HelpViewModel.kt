package com.example.helpersapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.helpersapp.model.HelpNeeded
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HelpViewModel: ViewModel()  {

    private val _newHelpNeeded = MutableStateFlow(HelpNeeded())
    val newHelpNeeded: StateFlow<HelpNeeded> = _newHelpNeeded.asStateFlow()

    fun changeWorkDetails(newWorkDetails: String) {
        _newHelpNeeded.value = _newHelpNeeded.value.copy(workDetails = newWorkDetails)
    }
    fun changePriceRange(newPriceRange: ClosedFloatingPointRange<Float>) {
        _newHelpNeeded.value = _newHelpNeeded.value.copy(priceRange = newPriceRange)
    }
}