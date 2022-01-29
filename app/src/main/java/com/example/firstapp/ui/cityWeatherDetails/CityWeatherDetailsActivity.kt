package com.example.firstapp.ui.cityWeatherDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.firstapp.adapters.WeatherForecastAdapter
import com.example.firstapp.databinding.ActivityCityWeatherDetailsBinding
import com.example.firstapp.model.LocationModel
import com.example.firstapp.model.DataPoint
import com.example.firstapp.ui.localization.LocalizationActivity
import com.example.firstapp.utils.WeatherInteraction
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.util.*

class CityWeatherDetailsActivity : LocalizationActivity() {

    private var forecastAdapter: WeatherForecastAdapter? = null

    private lateinit var binding: ActivityCityWeatherDetailsBinding

    var view: View? = null
    var city: String = ""
    var lat: String = ""
    var lon: String = ""

    private val cityNameExtras = "cityName"
    private val latExtras = "lat"
    private val lonExtras = "lon"
    private val CITY_NAME_EXTRA = "cityName"
    private val LAT_EXTRA = "lat"
    private val LON_EXTRA = "lon"

    private val scope: CoroutineScope= CoroutineScope(SupervisorJob() + Dispatchers.IO)
    lateinit var cityWeatherDetailsActivityViewModel: CityWeatherDetailsActivityViewModel
    lateinit var locationModel: LocationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCityWeatherDetailsBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.forecastRecycle.addItemDecoration(dividerItemDecoration)
        forecastAdapter = WeatherForecastAdapter(this)
        binding.forecastRecycle.adapter = forecastAdapter

        cityWeatherDetailsActivityViewModel = ViewModelProvider(this)[CityWeatherDetailsActivityViewModel::class.java]

        processComingIntent()

        locationModel = LocationModel(locationName = city, lat = lat, lon = lon)

        binding.cityName.text = city

        displayWeather()
        scope.launch(context = Dispatchers.IO) {
            WeatherInteraction.checkWeatherOnApi(locationModel)
        }

    }

    private fun processComingIntent() {
        intent.extras?.run {
            city = getString(cityNameExtras) ?: ""
            lat = getString(latExtras) ?: ""
            lon = getString(lonExtras) ?: ""
        }
        if (city.isEmpty() && lat.isEmpty() && lon.isEmpty()) {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayWeather(){
        cityWeatherDetailsActivityViewModel.getWeather(locationModel).observe(this@CityWeatherDetailsActivity, Observer { weather ->
            if(weather == null) return@Observer
            binding.temp.text = weather.currentTemp + " C"
            Picasso.with(binding.currentWeatherIcon.context).load(weather.weatherIcon)
                .into(binding.currentWeatherIcon)
        })
        cityWeatherDetailsActivityViewModel.getForecast(locationModel).observe(this@CityWeatherDetailsActivity, Observer { forecast ->
            if(forecast == null) return@Observer
            val maxTempDataPoints = forecast.mapIndexed { index, item ->
                DataPoint(index + 1, item.maxTemp.toInt(), item.date)
            }
            forecastAdapter?.addForecast(forecast)
            binding.graphView.setData(maxTempDataPoints)
            binding.spinner.visibility = View.GONE
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}