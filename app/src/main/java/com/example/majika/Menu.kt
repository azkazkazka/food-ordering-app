package com.example.majika

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.MenuRVAdapter
import com.example.majika.model.MenuModel
import com.example.majika.response.ResponseMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Menu() : Fragment() {


//    var menuModel : ArrayList<MenuModel> = MainActivity().menuModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.menuRecyclerView)

        val retrofitTest = RetrofitClient()
        val retrofit : Retrofit = retrofitTest.getInstance()
        var apiInterface = retrofit.create(ApiInterface::class.java)
        var foodModel = ArrayList<MenuModel>()
        var drinkModel = ArrayList<MenuModel>()

        apiInterface.getAllMenu().enqueue(object : Callback<ResponseMenu> {
            override fun onResponse(
                call: Call<ResponseMenu>,
                response: Response<ResponseMenu>
            ) {
                if (response.isSuccessful()) {
                    //your code for handling success response
                    // move response.body() to locationModel
                    println(response)
                    println(response.body())
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
                    (activity as MainActivity).menuModel.clear()
                    (activity as MainActivity).menuModel.addAll(foodModel)
                    (activity as MainActivity).menuModel.addAll(drinkModel)

                } else {

                }

            }
            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }
        })

//        val adapter: MenuRVAdapter = MenuRVAdapter(menuModel)
//        recyclerView.adapter = adapter
        val adapter: MenuRVAdapter = MenuRVAdapter((activity as MainActivity).menuModel)
        recyclerView.adapter = adapter
        println("MASUKKK")
        println((activity as MainActivity).menuModel)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}