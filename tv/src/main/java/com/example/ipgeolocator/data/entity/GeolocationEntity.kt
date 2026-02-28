package com.example.ipgeolocator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geolocation")
data class GeolocationEntity(

    @PrimaryKey
    val query: String,

    val status: String,
    val country: String?,
    val countryCode: String?,
    val region: String?,
    val regionName: String?,
    val city: String?,
    val zip: String?,
    val lat: Double?,
    val lon: Double?,
    val timezone: String?

)