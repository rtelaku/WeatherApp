package com.example.firstapp.alarmManager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firstapp.ui.cityWeatherDetails.CityWeatherDetailsActivity
import com.example.firstapp.R
import com.example.firstapp.utils.WeatherApp
import com.example.firstapp.utils.WeatherPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

object WeatherNotificationInteraction {

    fun showWeatherNotification(context: Context, intent: Intent){
        CoroutineScope(Dispatchers.IO).launch {
            val city = WeatherPreferences.getLocationModel()
            if(city != null) {
                val cityDatabase = WeatherApp.getInstance().getDb().weatherDao().getSelectedCityWeather(city.lat, city.lon)
                val cityIntent = Intent(context, CityWeatherDetailsActivity::class.java)
                with(cityIntent) {
                    putExtra("cityName", city.locationName)
                    putExtra("lat", city.lat)
                    putExtra("lon", city.lon)
                }

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, cityIntent, 0)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name : CharSequence = "weatherChannelIdReminder"
                    val description = "Weather today"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel("weatherChannelId", name, importance)
                    channel.description = description

                    val notificationManager = WeatherApp.getInstance().getSystemService(NotificationManager::class.java)
                    notificationManager.createNotificationChannel(channel)

                }

                val notificationBuilder = NotificationCompat.Builder(context, "weatherChannelId")
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle(city.locationName)
                    .setContentText("Weather today: ${cityDatabase.currentTemp} C")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)

                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(200, notificationBuilder.build())

            }
        }
    }

    fun scheduleNotificationAlarm(){
        val intent = Intent(WeatherApp.getInstance(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(WeatherApp.getInstance(), 0, intent, 0)
        val alarmManager : AlarmManager = WeatherApp.getInstance().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        val now = Calendar.getInstance()
        val cal = Calendar.getInstance()
        cal.apply {
            cal[Calendar.HOUR_OF_DAY] = 8
            cal[Calendar.MINUTE] = 0
            cal[Calendar.SECOND] = 0
        }

        if (now.before(cal)) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
        } else {
            cal[Calendar.DAY_OF_YEAR] = now[Calendar.DAY_OF_YEAR] + 1
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
        }
    }
}