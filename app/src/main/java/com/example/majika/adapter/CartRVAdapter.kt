package com.example.majika.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.MainActivity
import com.example.majika.Cart
import com.example.majika.R
import com.example.majika.model.MenuModel

class CartRVAdapter(private val cartFragment: Cart, private val mList: List<MenuModel>) :

    RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_cart, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var menuModel = mList[position]

        holder.header.visibility = View.GONE
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
            for(i in 0 until (cartFragment.activity as MainActivity).updateMenuList.size){
                if(
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_name == menuModel.get_name &&
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_description == menuModel.get_description &&
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_price == menuModel.get_price &&
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_type == menuModel.get_type
                ){
                    (cartFragment.activity as MainActivity).updateMenuList[i] = newMenuModel
                    contains = true
                    break
                }
            }
            if(!contains){
                (cartFragment.activity as MainActivity).updateMenuList.add(newMenuModel)
            }
            cartFragment.viewModel.apply{
                insertCart((cartFragment.activity as MainActivity).updateMenuList)
                getPrice()
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
            for(i in 0 until (cartFragment.activity as MainActivity).updateMenuList.size){
                if(
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_name == menuModel.get_name &&
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_description == menuModel.get_description &&
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_price == menuModel.get_price &&
                    (cartFragment.activity as MainActivity).updateMenuList[i].get_type == menuModel.get_type
                ){
                    (cartFragment.activity as MainActivity).updateMenuList[i] = newMenuModel
                    contains = true
                    break
                }
            }
            if(!contains){
                (cartFragment.activity as MainActivity).updateMenuList.add(newMenuModel)
            }
            if(quantity == 0){
                holder.body.visibility = View.GONE
            }
            cartFragment.viewModel.apply{
                insertCart((cartFragment.activity as MainActivity).updateMenuList)
                getPrice()
            }
        })

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
        val header: TextView = itemView.findViewById(R.id.typeHeader)
        val body: CardView = itemView.findViewById(R.id.typeBody)
        val add: Button = itemView.findViewById(R.id.add)
        val sub: Button = itemView.findViewById(R.id.sub)
        val qty: TextView = itemView.findViewById(R.id.quantity)
    }
}