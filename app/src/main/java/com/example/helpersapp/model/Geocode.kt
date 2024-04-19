package com.example.helpersapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Geocode {
    @GET("json")
    suspend fun getGeocode(
            @Query("address") address: String,
            @Query("key") key: String
    ): GeocodeResponse

    companion object{
        fun create(): Geocode{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .build()
            return retrofit.create(Geocode::class.java)
        }
    }
}