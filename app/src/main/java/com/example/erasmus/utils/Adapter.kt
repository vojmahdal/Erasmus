package com.example.erasmus.utils

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erasmus.R
import com.example.erasmus.database.LocationData

class Adapter(private val locationList : ArrayList<LocationData>) : RecyclerView.Adapter<Adapter.LocationViewHolder>() {
//class Adapter(private val locationList : ArrayList<LocationData>, private val listener: OnClickListener) : RecyclerView.Adapter<Adapter.LocationViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.location_item,
            parent, false)
        return LocationViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentitem = locationList[position]

        holder.locationName.text = currentitem.locationName
        holder.locationCountry.text = currentitem.locationCountry


    }

    class LocationViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val locationName : TextView = itemView.findViewById(R.id.tvlocationName)
        val locationCountry : TextView = itemView.findViewById(R.id.tvlocationCountry)
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}