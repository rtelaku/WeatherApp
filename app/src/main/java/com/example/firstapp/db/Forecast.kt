package com.example.firstapp.db

import androidx.room.*
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = Weather::class,
    parentColumns = ["cityLatitude", "cityLongitude"],
    childColumns = ["cityLatitude", "cityLongitude"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["cityLatitude", "cityLongitude"])],
    inheritSuperIndices = true)
class Forecast(
    var icon: String,
    var date: Date,
    var minTemp: String,
    var maxTemp: String,
    var cityLatitude: String,
    var cityLongitude: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}