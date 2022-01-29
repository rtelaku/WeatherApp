package com.example.firstapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class GetWeather() {
    @SerializedName("current")
    @Expose
    var main: Current? = null

    @SerializedName("daily")
    @Expose
    lateinit var daily: Array<Daily>
}