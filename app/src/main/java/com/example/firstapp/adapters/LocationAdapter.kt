package com.example.firstapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.utils.WeatherPreferences
import com.example.firstapp.databinding.RowItemBinding
import com.example.firstapp.databinding.RowSubitemBinding
import com.example.firstapp.enums.LocationType
import com.example.firstapp.model.LocationModel
import com.example.firstapp.services.OnItemClickListener
import java.util.ArrayList

class LocationAdapter(
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>(), OnItemClickListener {

    private val COUNTRY = 0
    private val CITY = 1
    private var cityList = ArrayList<LocationModel>()

    val currentSelectedCity get() = WeatherPreferences.getLocationModel()

    @SuppressLint("NotifyDataSetChanged")
    inner class CityViewHolder(itemView: View) : ViewHolder(itemView) {
        private var binding: RowSubitemBinding = RowSubitemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        override fun addData(locationModel: LocationModel) {
            binding.subitem.text = "- " + locationModel.locationName
            binding.subItemRadioButton.isChecked = cityList[bindingAdapterPosition].locationName == currentSelectedCity?.locationName;
        }

        init {
            binding.subItemRadioButton.setOnClickListener(View.OnClickListener {
                val locationModel = cityList[bindingAdapterPosition]
                clickListener.checkedItem(locationModel)
                notifyDataSetChanged()
            })

            itemView.setOnClickListener {
                val bindingAdapterPosition = bindingAdapterPosition
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val locationModel = cityList[bindingAdapterPosition]
                    clickListener.onClick(locationModel)
                }
            }
        }
    }

    fun setAdapterList(list: ArrayList<LocationModel>) {
        cityList = list
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(itemView: View) : ViewHolder(itemView) {
        private var binding: RowItemBinding = RowItemBinding.bind(itemView)
        override fun addData(locationModel: LocationModel) {
            binding.recycleItem.text = locationModel.locationName

        }
    }

    override fun getItemViewType(position: Int): Int {
        val locationModel = cityList[position]
        return if (locationModel.locationType == LocationType.COUNTRY) {
            COUNTRY
        } else {
            CITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == COUNTRY) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
            CountryViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.row_subitem, parent, false)
            CityViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addData(cityList[position])
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun addData(locationModel: LocationModel)
    }
}