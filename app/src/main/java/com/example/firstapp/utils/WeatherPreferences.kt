package com.example.firstapp.utils

import android.content.Context
import com.example.firstapp.model.LocationModel
import com.google.gson.Gson

object WeatherPreferences {

    private val prefs = WeatherApp.getInstance().getSharedPreferences("WeatherPrefs", Context.MODE_PRIVATE)

    fun saveLocationModel(model: LocationModel?) {
        prefs.edit()?.putString("City", Gson().toJson(model))?.apply()
    }

    fun saveLanguage(language: String?) {
        prefs.edit()?.putString("Language", language)?.commit()
    }

    fun getLocationModel(): LocationModel? {
        val json = prefs?.getString("City", "")
        return Gson().fromJson(json, LocationModel::class.java)
    }

    fun getLanguage(): String {
        return prefs.getString("Language", "en")!!
    }
}

