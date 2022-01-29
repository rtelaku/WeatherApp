package com.example.firstapp.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import java.util.*

object LocalizationUtils {

    fun setLocale(resources: Resources, lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val config = resources.configuration
        config.setLocale(myLocale)
        config.setLayoutDirection(myLocale)
        res.updateConfiguration(config, dm)
    }

}