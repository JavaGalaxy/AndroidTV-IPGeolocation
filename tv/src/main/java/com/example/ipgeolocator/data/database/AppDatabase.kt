package com.example.ipgeolocator.data.database

import androidx.room.Room
import android.content.Context
import com.example.ipgeolocator.data.dao.GeolocationDAO
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ipgeolocator.data.entity.GeolocationEntity

@Database(
    entities = [GeolocationEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun geolocationDao(): GeolocationDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            } else {
                synchronized(this) {
                    if (INSTANCE == null) {
                        val instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "geolocation"
                        ).build()
                        INSTANCE = instance
                    }
                }
                return INSTANCE!!
            }
        }
    }
}