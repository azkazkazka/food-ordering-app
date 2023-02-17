package com.example.majika.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.majika.MainActivity
import com.example.majika.data.AppDatabase
import com.example.majika.data.dao.MenuDBDao
import com.example.majika.data.entity.MenuDB
import com.example.majika.model.LocationModel
import com.example.majika.model.MenuModel
import com.example.majika.response.ResponseLocation
import com.example.majika.response.ResponseMenu
import com.example.majika.retrofit.ApiInterface
import com.example.majika.retrofit.ApiUtils
import com.example.majika.retrofit.ApiUtils.Companion.getApiInterface
import kotlinx.coroutines.delay

class Repository(context: Context) {
    val menuList = MutableLiveData<List<MenuModel>>()
    val searchList = MutableLiveData<List<MenuModel>>()
    val cartList = MutableLiveData<List<MenuDB>>()
    val locationList = MutableLiveData<List<LocationModel>>()
    val paymentStatus = MutableLiveData<String>()

    private val api: ApiInterface = ApiUtils.getApiInterface()
//    private val room: MenuDBDao? =
//        AppDatabase.getInstance(context)?.menuDBDao()

    suspend fun getMenu() {
        try{
            delay(500)
            val response = api.getAllMenu()
            var foodModel = mutableListOf<MenuModel>()
            var drinkModel = mutableListOf<MenuModel>()
            if (response.isSuccessful) {
                for (data in response.body()!!.data) {
                    if(data.type == "Food"){
                        foodModel.add(
                            MenuModel(
                                data.name,
                                data.description,
                                data.currency,
                                data.price,
                                data.sold,
                                data.type,
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
//    suspend fun getAll() = room?.getAll()
//
//    suspend fun loadAllByIds(menuIds: IntArray) = room?.loadAllByIds(menuIds)
//
//    suspend fun findByName(name: String) = room?.findByName(name)
//
//    suspend fun insertAll(vararg menu: MenuDB) = room?.insertAll(*menu)
//
//    suspend fun update(name: String, quantity: Int) = room?.update(name, quantity)
//
//    suspend fun delete(menu: MenuDB) = room?.delete(menu)
}