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
    var searchList : LiveData<List<MenuModel>> = repository.searchList

    fun getMenu(){
        viewModelScope.launch {
            repository.getMenu()
        }
    }
}
