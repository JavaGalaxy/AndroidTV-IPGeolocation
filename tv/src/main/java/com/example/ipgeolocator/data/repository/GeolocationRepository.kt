package com.example.ipgeolocator.data.repository

import com.example.ipgeolocator.data.dao.GeolocationDAO
import com.example.ipgeolocator.data.entity.GeolocationEntity
import com.example.ipgeolocator.data.remote.ApiClient

class GeolocationRepository(
    private val dao: GeolocationDAO
) {
    suspend fun getGeolocation(ipAddress: String): GeolocationEntity? {
        val cachedLocalData = dao.getByIpAddress(ipAddress)
        if (cachedLocalData != null) {
            return cachedLocalData
        }

        try {
            val apiResponse = ApiClient.ipApiService.getGeolocation(ipAddress)

            val entity = GeolocationEntity(
                query = apiResponse.query,
                status = apiResponse.status,
                country = apiResponse.country,
                countryCode = apiResponse.countryCode,
                region = apiResponse.region,
                regionName = apiResponse.regionName,
                city = apiResponse.city,
                zip = apiResponse.zip,
                lat = apiResponse.lat,
                lon = apiResponse.lon,
                timezone = apiResponse.timezone
            )

            dao.insert(entity)
            return entity

        } catch (e: Exception) {
            return null
        }
    }
}