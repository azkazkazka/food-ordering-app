package com.example.majika.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.majika.repository.Repository
import kotlinx.coroutines.launch

class PaymentViewModel(context: Context) : ViewModel() {
    private val repository = Repository(context)
    var paymentStatus : LiveData<String> = repository.paymentStatus

    fun postPayment(code: String){
        viewModelScope.launch {
            repository.postPayment(code)
        }
    }
}