package com.example.firstapp.model

import com.example.firstapp.enums.LocationType

data class LocationModel(
    var locationName: String,
    var locationType: LocationType ?= null,
    var lat: String,
    var lon: String
)