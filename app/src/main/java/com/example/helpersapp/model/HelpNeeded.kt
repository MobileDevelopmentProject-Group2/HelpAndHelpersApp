package com.example.helpersapp.model

data class HelpNeeded(
    var category: String = "",
    var workDetails: String = "",
    var date: String = "",
    var time: String = "",
    var priceRange: ClosedFloatingPointRange<Float> = 0.0f..100.0f,
    var postalCode: String = "",
    var userId: String = "",
    var requestPostDate: String = "",
    var userEmail: String = ""
)