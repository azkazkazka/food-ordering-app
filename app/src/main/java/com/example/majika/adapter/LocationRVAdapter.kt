package com.example.majika.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.model.LocationModel
import com.example.majika.R

class LocationRVAdapter(private val mList: List<LocationModel>) :
    RecyclerView.Adapter<LocationRVAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_location, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.cabangResto.text = locationModel.get_name
        holder.lokasiResto.text = locationModel.get_address

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cabangResto: TextView = itemView.findViewById(R.id.cabangRestoran)
        val lokasiResto: TextView = itemView.findViewById(R.id.lokasiRestoran)
    }
}