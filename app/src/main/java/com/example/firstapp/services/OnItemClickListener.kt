package com.example.firstapp.services

import com.example.firstapp.model.LocationModel

interface OnItemClickListener {
    fun onClick(item: LocationModel) {}

    fun checkedItem(item: LocationModel) {}

}