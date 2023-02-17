package com.example.majika.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.majika.model.LocationModel
import com.example.majika.repository.Repository
import kotlinx.coroutines.launch

class LocationViewModel(context: Context) : ViewModel() {
    private val repository = Repository(context)
    var locationList : LiveData<List<LocationModel>> = repository.locationList

    fun getLocation(){
        viewModelScope.launch {
            repository.getLocation()
        }
    }
}