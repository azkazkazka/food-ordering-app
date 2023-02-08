package com.example.majika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationRVAdapter(private val mList: List<MenuModel>) :
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
        val menuModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.cabangResto.text = menuModel.cabang_restoran
        holder.deskripsiResto.text = menuModel.deskripsi

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cabangResto: TextView = itemView.findViewById(R.id.cabangRestoran)
        val deskripsiResto: TextView = itemView.findViewById(R.id.deskripsiRestoran)
    }
}