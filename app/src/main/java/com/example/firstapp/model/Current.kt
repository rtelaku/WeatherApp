package com.example.firstapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Current(
    var id: Int = 0,

    @SerializedName("temp")
    @Expose
    var temp: Double? = null,

    @SerializedName("weather")
    @Expose
    var weather: List<CurrentWeatherDetails>? = null,
)