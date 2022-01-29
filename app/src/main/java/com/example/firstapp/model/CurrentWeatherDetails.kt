package com.example.firstapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CurrentWeatherDetails(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("icon")
    @Expose
    var icon: String? = null,
)