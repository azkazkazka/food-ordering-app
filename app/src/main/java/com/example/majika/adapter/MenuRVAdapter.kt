package com.example.majika.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.MainActivity
import com.example.majika.Menu
import com.example.majika.R
import com.example.majika.model.MenuModel

class MenuRVAdapter(private val menuFragment: Menu, private var mList: List<MenuModel>) :

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
        var menuModel = mList[position]

        if (position == 0 && menuModel.get_type == "Food") {
            holder.header.text = "Makanan"
            holder.header.visibility = View.VISIBLE
        } else if (position == 0 && menuModel.get_type == "Drink") {
            holder.header.text = "Makanan"
            holder.header.visibility = View.VISIBLE
        } else if (mList[position - 1].get_type == "Food" && menuModel.get_type == "Drink") {
            holder.header.text = "Minuman"
            holder.header.visibility = View.VISIBLE
        } else {
            holder.header.visibility = View.GONE
        }
        holder.menu.text = menuModel.get_name
        holder.harga.text =  menuModel.get_currency + menuModel.get_price.toString()
        holder.terjual.text = "Terjual : " + menuModel.get_sold.toString()
        holder.deskripsi.text = menuModel.get_description
        holder.qty.text = menuModel.get_quantity.toString()
        holder.add.setOnClickListener(View.OnClickListener { v ->
            var quantity = holder.qty.text.toString().toInt()
            quantity = quantity + 1
            holder.qty.text = quantity.toString()
            // if updateMenuModel contain menuModel, update the quantity
            // else add menuModel to updateMenuModel
            var newMenuModel = MenuModel(
                menuModel.get_name,
                menuModel.get_description,
                menuModel.get_currency,
                menuModel.get_price,
                menuModel.get_sold,
                menuModel.get_type,
                quantity
            )
            var contains = false
            for(i in 0 until (menuFragment.activity as MainActivity).updateMenuList.size){
                if((menuFragment.activity as MainActivity).updateMenuList[i].get_name == menuModel.get_name){
                    (menuFragment.activity as MainActivity).updateMenuList[i] = newMenuModel
                    contains = true
                    break
                }
            }
            if(!contains){
                (menuFragment.activity as MainActivity).updateMenuList.add(newMenuModel)
            }
            menuFragment.viewModel.apply{
                insertCart((menuFragment.activity as MainActivity).updateMenuList)
                getMenu()
            }
            if(!menuFragment.filteredList.isEmpty()){
                menuFragment.filteredList[position] = newMenuModel
            }
        })
        holder.sub.setOnClickListener(View.OnClickListener { v ->
            var quantity = holder.qty.text.toString().toInt()
            if (quantity > 0) {
                quantity = quantity - 1
                holder.qty.text = quantity.toString()
            }
            // if updateMenuModel contain menuModel, update the quantity
            // else add menuModel to updateMenuModel
            var newMenuModel = MenuModel(
                menuModel.get_name,
                menuModel.get_description,
                menuModel.get_currency,
                menuModel.get_price,
                menuModel.get_sold,
                menuModel.get_type,
                quantity
            )
            println(quantity)
            var contains = false
            for(i in 0 until (menuFragment.activity as MainActivity).updateMenuList.size){
                if((menuFragment.activity as MainActivity).updateMenuList[i].get_name == menuModel.get_name){
                    (menuFragment.activity as MainActivity).updateMenuList[i] = newMenuModel
                    contains = true
                    break
                }
            }
            if(!contains){
                (menuFragment.activity as MainActivity).updateMenuList.add(newMenuModel)
            }
            menuFragment.viewModel.apply{
                insertCart((menuFragment.activity as MainActivity).updateMenuList)
                getMenu()
            }
            if(!menuFragment.filteredList.isEmpty()){
                menuFragment.filteredList[position] = newMenuModel
            }
        })

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun filterList(filterlist: ArrayList<MenuModel>) {
        println("filterlist : $filterlist")
        mList = filterlist
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val menu: TextView = itemView.findViewById(R.id.menuRestoran)
        val harga: TextView = itemView.findViewById(R.id.hargaMenu)
        val terjual: TextView = itemView.findViewById(R.id.terjualMenu)
        val deskripsi: TextView = itemView.findViewById(R.id.deskripsiMenu)
        val header: TextView = itemView.findViewById(R.id.typeHeader)
        val add: Button = itemView.findViewById(R.id.add)
        val sub: Button = itemView.findViewById(R.id.sub)
        val qty: TextView = itemView.findViewById(R.id.quantity)
    }
}
