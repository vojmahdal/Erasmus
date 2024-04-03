package com.example.erasmus.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erasmus.R
import com.example.erasmus.database.LocationData

class Adapter(private val locationList : ArrayList<LocationData>) : RecyclerView.Adapter<Adapter.LocationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.location_item,
            parent, false)
        return LocationViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentitem = locationList[position]

        holder.locationName.text = currentitem.locationName
        holder.locationCountry.text = currentitem.locationCountry
    }
    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val locationName : TextView = itemView.findViewById(R.id.tvlocationName)
        val locationCountry : TextView = itemView.findViewById(R.id.tvlocationCountry)
    }
}