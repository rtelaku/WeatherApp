package com.example.firstapp.db

import androidx.room.*
import java.util.*

object Converters {
    @kotlin.jvm.JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @kotlin.jvm.JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}