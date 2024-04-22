package com.example.helpersapp.model
data class User(
    var firstname: String = "",
    var lastname: String = "",
    var email: String = "",
    var address: String = "",
    var username: String = "",
    //add user uid to slove fetch uid
    var uid: String =""
)