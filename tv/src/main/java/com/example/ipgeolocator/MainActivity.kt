package com.example.ipgeolocator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ipgeolocator.ui.viewmodel.GeolocationViewModel
import com.example.ipgeolocator.data.repository.GeolocationRepository
import com.example.ipgeolocator.data.database.AppDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etIpAddress = findViewById<EditText>(R.id.etIpAddress)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvResults = findViewById<TextView>(R.id.tvResults)

        val database = AppDatabase.getDatabase(this)
        val repository = GeolocationRepository(database.geolocationDao())
        val factory = GeolocationViewModelFactory(repository)
        val viewModel: GeolocationViewModel by viewModels { factory }

        btnSearch.setOnClickListener {
            val ipAddress = etIpAddress.text.toString().trim()
            if (ipAddress.isNotEmpty()) {
                viewModel.searchGeolocation(ipAddress)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.geolocationData.observe(this) { data ->
            if (data != null) {
                tvResults.text = formatGeolocationData(data)
            }
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                tvResults.text = "Error: $error"
            }
        }
    }

    private fun formatGeolocationData(data: com.example.ipgeolocator.data.entity.GeolocationEntity): String {
        return buildString {
            appendLine("IP Address: ${data.query}")
            appendLine("Status: ${data.status}")
            appendLine("Country: ${data.country ?: "Unknown"}")
            appendLine("Region: ${data.regionName ?: "Unknown"}")
            appendLine("City: ${data.city ?: "Unknown"}")
            appendLine("Latitude: ${data.lat ?: "Unknown"}")
            appendLine("Longitude: ${data.lon ?: "Unknown"}")
            appendLine("Timezone: ${data.timezone ?: "Unknown"}")
        }
    }
}

class GeolocationViewModelFactory(
    private val repository: GeolocationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeolocationViewModel::class.java)) {
            return GeolocationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}