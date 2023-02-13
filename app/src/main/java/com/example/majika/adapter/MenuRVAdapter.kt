package com.example.majika.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.model.MenuModel



class MenuRVAdapter(private val mList: List<MenuModel>) :
    RecyclerView.Adapter<MenuRVAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_menu, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.menu.text = menuModel.get_name
        //benerin format harga
        holder.harga.text =  menuModel.get_currency + menuModel.get_price.toString()
        holder.terjual.text = "Terjual : " + menuModel.get_sold.toString()
        holder.deskripsi.text = menuModel.get_description

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val menu: TextView = itemView.findViewById(R.id.menuRestoran)
        val harga: TextView = itemView.findViewById(R.id.hargaMenu)
        val terjual: TextView = itemView.findViewById(R.id.terjualMenu)
        val deskripsi: TextView = itemView.findViewById(R.id.deskripsiMenu)
    }
}
