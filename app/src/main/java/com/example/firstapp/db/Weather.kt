package com.example.firstapp.db

import androidx.room.*

@Entity(primaryKeys = ["cityLatitude", "cityLongitude"],
    indices = [Index(value = ["cityLatitude", "cityLongitude"], unique = true)],
    inheritSuperIndices = true)
class Weather(
    var cityName: String,
    var currentTemp: String,
    var cityLatitude: String,
    var cityLongitude: String,
    var weatherIcon: String
)