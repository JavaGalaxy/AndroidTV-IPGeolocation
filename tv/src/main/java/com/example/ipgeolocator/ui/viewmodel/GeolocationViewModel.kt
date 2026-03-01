package com.example.ipgeolocator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipgeolocator.data.entity.GeolocationEntity
import com.example.ipgeolocator.data.repository.GeolocationRepository
import kotlinx.coroutines.launch

class GeolocationViewModel(
    private val repository: GeolocationRepository
) : ViewModel() {
    private val _geolocationData = MutableLiveData<GeolocationEntity?>()
    val geolocationData: LiveData<GeolocationEntity?> = _geolocationData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun searchGeolocation(ipAddress: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getGeolocation(ipAddress)
            _geolocationData.value = result

            if (result == null) {
                _error.value = "Failed to get geolocation data"
            }

            _isLoading.value = false
        }
    }
}