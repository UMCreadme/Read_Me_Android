package com.example.readme.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R

class LocationAdapter(
    private val locations: List<LocationResult>,
    private val onItemClick: (LocationResult) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location)
    }

    override fun getItemCount() = locations.size

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)

        fun bind(location: LocationResult) {
            addressTextView.text = location.formatted_address
            itemView.setOnClickListener {
                onItemClick(location)
            }
        }
    }
}
