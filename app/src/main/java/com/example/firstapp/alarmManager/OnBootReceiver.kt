package com.example.firstapp.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.firstapp.utils.WeatherPreferences

class OnBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if(WeatherPreferences.getLocationModel() != null){
            WeatherNotificationInteraction.scheduleNotificationAlarm()
        }
    }
}
