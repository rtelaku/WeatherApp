package com.example.firstapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.firstapp.R
import com.example.firstapp.adapters.LocationAdapter
import com.example.firstapp.databinding.ActivityMainWeatherBinding
import com.example.firstapp.model.LocationModel
import com.example.firstapp.services.OnItemClickListener
import com.example.firstapp.ui.cityWeatherDetails.CityWeatherDetailsActivity
import com.example.firstapp.ui.localization.LocalizationActivity
import com.example.firstapp.utils.LocalizationUtils
import com.example.firstapp.utils.WeatherPreferences
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainWeatherActivity : LocalizationActivity(), OnItemClickListener {

    private var recyclerAdapter: LocationAdapter? = null
    private val CITY_NAME_EXTRA = "cityName"
    private val LAT_EXTRA = "lat"
    private val LON_EXTRA = "lon"
    lateinit var mainViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainWeatherBinding = ActivityMainWeatherBinding.inflate(layoutInflater)

        val view: View = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerAdapter = LocationAdapter(this)
        binding.recyclerView.adapter = recyclerAdapter

        displayLocations()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.language -> {
                true
            }
            R.id.albanianLanguageItem -> {
                setLanguage("sq")
                true
            }
            R.id.englishLanguageItem -> {
                setLanguage("en")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLanguage(lang: String?) {
        LocalizationUtils.setLocale(resources, lang)
        WeatherPreferences.saveLanguage(lang)
        refreshApp()
    }

    private fun refreshApp(){
        val refresh = Intent(this, MainWeatherActivity::class.java)
        finish()
        startActivity(refresh)
    }

    private fun displayLocations() {
        mainViewModel.getCityLocations().observe(this, {
            recyclerAdapter?.setAdapterList(ArrayList(it))
            recyclerAdapter?.notifyDataSetChanged()
        })
    }

    override fun onClick(item: LocationModel) {
        val intent = Intent(this@MainWeatherActivity, CityWeatherDetailsActivity::class.java)
        with(intent) {
            putExtra(CITY_NAME_EXTRA, item.locationName)
            putExtra(LAT_EXTRA, item.lat)
            putExtra(LON_EXTRA, item.lon)
        }
        startActivity(intent)
    }

    override fun checkedItem(item: LocationModel) {
        WeatherPreferences.saveLocationModel(item)
    }
}