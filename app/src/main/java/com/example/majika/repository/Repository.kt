package com.example.majika.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.majika.data.AppDatabase
import com.example.majika.data.dao.MenuDBDao
import com.example.majika.data.entity.MenuDB
import com.example.majika.model.LocationModel
import com.example.majika.model.MenuModel
import com.example.majika.retrofit.ApiInterface
import com.example.majika.retrofit.ApiUtils
import kotlinx.coroutines.delay

class Repository(context: Context) {
    val menuList = MutableLiveData<List<MenuModel>>()
    val searchList = MutableLiveData<List<MenuModel>>()
    val cartList = MutableLiveData<List<MenuModel>>()
    val locationList = MutableLiveData<List<LocationModel>>()
    val paymentStatus = MutableLiveData<String>()
    val totalPrice = MutableLiveData<Int>()

    private val api: ApiInterface = ApiUtils.getApiInterface()
    private val room: MenuDBDao? =
        AppDatabase.getInstance(context)?.menuDBDao()

    suspend fun getMenu() {
        try{
            delay(500)
            val response = api.getAllMenu()
            var foodModel = mutableListOf<MenuModel>()
            var drinkModel = mutableListOf<MenuModel>()
            if (response.isSuccessful) {
                for (data in response.body()!!.data) {
                    var quantity = room?.getQuantity(data.name, data.description, data.price, data.type)
                    if(quantity == null){
                        quantity = 0
                    }
                    if(data.type == "Food"){
                        foodModel.add(
                            MenuModel(
                                data.name,
                                data.description,
                                data.currency,
                                data.price,
                                data.sold,
                                data.type,
                                quantity,
                            )
                        )
                    }
                    else{
                        drinkModel.add(
                            MenuModel(
                                data.name,
                                data.description,
                                data.currency,
                                data.price,
                                data.sold,
                                data.type,
                                quantity,
                            )
                        )
                    }
                }
                foodModel.addAll(drinkModel)
                menuList.value = foodModel
            } else {

            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }

    suspend fun getLocation(){
        try{
            delay(500)
            val response = api.getAllBranch()
            var locationModel = mutableListOf<LocationModel>()
            if (response.isSuccessful) {
                for (data in (response.body()!!.data).sortedBy { it.name }) {
                    locationModel.add(
                        LocationModel(
                            data.name,
                            data.popular_food,
                            data.address,
                            data.contact_person,
                            data.phone_number,
                            data.longitude,
                            data.latitude
                        )
                    )
                }
                locationList.value = locationModel
            } else {

            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun postPayment(code: String){
        try{
            delay(500)
            val response = api.postPayment(code)
            if (response.isSuccessful) {
                paymentStatus.value = response.body()!!.status
            } else {

            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun insertCart(menu: List<MenuModel> ){
        //delete old data
        room?.delete()
        //insert new data
        try{
            delay(500)
            for (data in menu) {
                room?.insert(
                    MenuDB(
                        null,
                        data.get_name,
                        data.get_description,
                        data.get_currency,
                        data.get_price,
                        data.get_sold,
                        data.get_type,
                        data.get_quantity
                    )
                )
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun getCart(){
        try{
            delay(500)
            var tempCartList = room?.getAll()
            //convert tempcartlist to menu model
            var cartModel = mutableListOf<MenuModel>()
            if(tempCartList != null){
                for (data in tempCartList) {
                    if (data.quantity!! != 0) {
                        cartModel.add(
                            MenuModel(
                                data.name!!,
                                data.description!!,
                                data.currency!!,
                                data.price!!,
                                data.sold!!,
                                data.type!!,
                                data.quantity!!
                            )
                        )
                    }
                }
                cartList.value = cartModel
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun getPrice(){
        try{
            delay(500)
            var tempCartList = room?.getAll()
            var price = 0
            if(tempCartList != null){
                for (data in tempCartList) {
                    if (data.quantity!! != 0) {
                        price += data.price!! * data.quantity!!
                    }
                }
                totalPrice.value = price
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}