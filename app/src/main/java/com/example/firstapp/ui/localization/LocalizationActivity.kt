package com.example.firstapp.ui.localization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.utils.LocalizationUtils
import com.example.firstapp.utils.WeatherPreferences

abstract class LocalizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupLanguage()
        super.onCreate(savedInstanceState)
    }

    private fun setupLanguage(){
        LocalizationUtils.setLocale(resources,WeatherPreferences.getLanguage())
    }
}