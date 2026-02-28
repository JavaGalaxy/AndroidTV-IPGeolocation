package com.example.ipgeolocator.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface IpApiService {
    @GET("json/{ip}")
    suspend fun getGeolocation(@Path("ip") ipAddress: String): GeolocationResponse
}

object ApiClient {
    private const val BASE_URL = "http://ip-api.com/"

    private val retrofit = Retrofit
                            .Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

    val ipApiService: IpApiService = retrofit.create(IpApiService::class.java)
}