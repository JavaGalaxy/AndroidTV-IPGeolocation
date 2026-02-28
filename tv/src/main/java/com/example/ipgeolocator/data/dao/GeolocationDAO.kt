package com.example.ipgeolocator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ipgeolocator.data.entity.GeolocationEntity

@Dao
interface GeolocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(geolocation: GeolocationEntity)

    @Query("SELECT * FROM geolocation WHERE query = :ipAddress LIMIT 1")
    suspend fun getByIpAddress(ipAddress: String): GeolocationEntity?

    @Query("DELETE FROM geolocation WHERE query = :ipAddress")
    suspend fun deleteByIpAddress(ipAddress: String)
}