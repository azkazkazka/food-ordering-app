package com.example.majika.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.majika.model.MenuModel
import com.example.majika.repository.Repository
import kotlinx.coroutines.launch

class CartViewModel(context: Context) : ViewModel() {
    private val repository = Repository(context)
    var cartList : LiveData<List<MenuModel>> = repository.cartList
    var totalPrice : LiveData<Int> = repository.totalPrice

    fun getCart(){
        viewModelScope.launch {
            repository.getCart()
        }

    }
    fun insertCart(menu: List<MenuModel>){
        viewModelScope.launch {
            repository.insertCart(menu)
        }
    }

    fun getPrice(){
        viewModelScope.launch {
            repository.getPrice()
        }
    }
}
