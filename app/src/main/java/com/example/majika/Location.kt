package com.example.majika

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.LocationRVAdapter
import com.example.majika.model.LocationModel
import com.example.majika.response.ResponseLocation
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit


class Location : Fragment() {

    public var locationModel = ArrayList<LocationModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun populateList(locationModel : ArrayList<LocationModel>) {
        this.locationModel = locationModel
    }

//    private fun populateList(locationObject : LocationModel) {
////        val names = getResources().getStringArray(R.array.amino_acids_full_txt)
////        val desc = getResources().getStringArray(R.array.amino_acids_three)
//        this.locationModel.add(locationObject)
//        // get array of string name from response json
//    }

    private fun getResponse() {
        val retrofitTest = RetrofitClient()
        val retrofit : Retrofit = retrofitTest.getInstance()
        val location = this
        var apiInterface = retrofit.create(ApiInterface::class.java)
        var locationModel = ArrayList<LocationModel>()

        apiInterface.getAllBranch().enqueue(object : Callback<ResponseLocation> {
            override fun onResponse(
                call: Call<ResponseLocation>,
                response: Response<ResponseLocation>
            ) {
                if (response.isSuccessful()) {
                    //your code for handling success response
                    // move response.body() to locationModel
                    println(response)
                    println(response.body())
                    for (data in response.body()!!.data) {
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
//                        location.populateList(LocationModel(
//                            data.name,
//                            data.popular_food,
//                            data.address,
//                            data.contact_person,
//                            data.phone_number,
//                            data.longitude,
//                            data.latitude
//                        ))
                    }
                    //init location class
//                    location.populateList(locationModel)
                    populateList(locationModel)
                    println(locationModel)
                    println(locationModel[0].get_name)
                } else {

                }

            }
            override fun onFailure(call: Call<ResponseLocation>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_location, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.mRecyclerView)

//        populateList()
//        this.locationModel = getResponse()
//        println(this.locationModel)
        getResponse()
        println(this.locationModel)
        val adapter: LocationRVAdapter = LocationRVAdapter(this.locationModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}