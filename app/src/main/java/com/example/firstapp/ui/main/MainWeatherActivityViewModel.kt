package com.example.firstapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.model.LocationModel

class MainActivityViewModel() : ViewModel() {
    private val mainWeatherRepository: MainWeatherRepository = MainWeatherRepository()

    fun getCityLocations(): LiveData<List<LocationModel>> {
        return mainWeatherRepository.getAllLocations()
    }
}