package com.example.firstapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.adapters.WeatherForecastAdapter.MyAdapter
import com.example.firstapp.databinding.ItemWeatherForecastBinding
import com.example.firstapp.db.Forecast
import com.example.firstapp.utils.WeatherPreferences
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherForecastAdapter(private val context: Context) : RecyclerView.Adapter<MyAdapter>() {
    @SuppressLint("SimpleDateFormat")
    private val format2 = SimpleDateFormat("EEEE",Locale(WeatherPreferences.getLanguage()))

    var items: List<Forecast> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_forecast, parent, false)
        return MyAdapter(view)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val forecastItem = items[position]
        holder.bindView(forecastItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addForecast(forecastList: List<Forecast>) {
        items = forecastList
        notifyDataSetChanged()
    }

    inner class MyAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemWeatherForecastBinding =  ItemWeatherForecastBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bindView(forecastItem: Forecast) {
            val iconUrl = forecastItem.icon
            val strDate = format2.format(forecastItem.date)
            binding.forecastDate.text = strDate
            val minTemp = forecastItem.minTemp
            val maxTemp = forecastItem.maxTemp
            Picasso.with(binding.forecastIcon.context).load(iconUrl).into(binding.forecastIcon)
            binding.forecastMinTemp.text = "Min: $minTemp C"
            binding.forecastMaxTemp.text = "Max: $maxTemp C"
        }
    }
}