package com.example.ipgeolocator.data.remote

import com.google.gson.annotations.SerializedName

data class GeolocationResponse (
    val status: String,
    val country: String?,
    val countryCode: String,
    val region: String,
    val regionName: String,
    val city: String?,
    val zip: String?,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val isp: String?,
    val org: String?,
    @SerializedName("as")
    val asData: String,
    val query: String,
)
