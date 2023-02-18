package com.example.majika.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.majika.data.entity.MenuDB
import com.example.majika.model.MenuModel
import com.example.majika.repository.Repository
import kotlinx.coroutines.launch

class MenuViewModel(context: Context) : ViewModel() {
    private val repository = Repository(context)
    var menuList : LiveData<List<MenuModel>> = repository.menuList

    fun getMenu(){
        viewModelScope.launch {
            repository.getMenu()
        }

    }
    fun insertCart(menu: List<MenuModel>){
        viewModelScope.launch {
            repository.insertCart(menu)
        }
    }
}
