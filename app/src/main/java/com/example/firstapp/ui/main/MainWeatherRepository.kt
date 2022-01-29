package com.example.firstapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstapp.enums.LocationType
import com.example.firstapp.model.LocationModel

class MainWeatherRepository {

    private val locationsList: MutableLiveData<List<LocationModel>> = MutableLiveData()

    fun getAllLocations(): LiveData<List<LocationModel>> {
        val locations = arrayListOf(
            LocationModel("Kosova", LocationType.COUNTRY, "0", "0"),
            LocationModel("Prishtina", LocationType.CITY, "42.6629", "21.1662"),
            LocationModel("Prizren", LocationType.CITY, "42.2171", "21.1662")
        )
        locationsList.value = locations
        return locationsList
    }

}