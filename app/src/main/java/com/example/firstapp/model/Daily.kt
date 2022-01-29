package com.example.firstapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Daily(
    @SerializedName("dt")
    @Expose
    var dt: Int? = null,

    @SerializedName("temp")
    @Expose
    var temp: Temp? = null,

    @SerializedName("weather")
    @Expose
    var weather: List<ForecastWeatherDetails>? = null,
)