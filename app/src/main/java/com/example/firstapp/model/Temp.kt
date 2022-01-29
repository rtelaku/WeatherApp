package com.example.firstapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Temp(
    @SerializedName("min")
    @Expose
    var min: Double? = null,

    @SerializedName("max")
    @Expose
    var max: Double? = null)
