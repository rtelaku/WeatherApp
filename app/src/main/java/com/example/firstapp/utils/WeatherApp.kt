package com.example.firstapp.utils

import android.app.Application
import android.content.Context
import com.example.firstapp.alarmManager.WeatherNotificationInteraction
import com.example.firstapp.db.WeatherDatabase
import com.example.firstapp.workManager.WorkerHelper
import java.util.*

class WeatherApp : Application() {
    private lateinit var weatherDatabase: WeatherDatabase
    override fun onCreate() {
        super.onCreate()
        weatherAppInstance = this
        weatherDatabase = WeatherDatabase.getDbInstance(this)

        WorkerHelper.startWeatherWorker()
    }

    companion object {
        lateinit var weatherAppInstance: WeatherApp
        fun getInstance(): WeatherApp {
            return weatherAppInstance
        }
    }

    fun getDb(): WeatherDatabase {
        return WeatherDatabase.getDbInstance(this.applicationContext)
    }
}
