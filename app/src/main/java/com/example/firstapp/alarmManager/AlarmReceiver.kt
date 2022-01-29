package com.example.firstapp.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        WeatherNotificationInteraction.showWeatherNotification(context, intent)
    }
}

