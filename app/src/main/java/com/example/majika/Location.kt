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
import retrofit2.Retrofit


class Location : Fragment() {
    private val menuModels = ArrayList<MenuModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun populateList() {
        val names = getResources().getStringArray(R.array.amino_acids_full_txt)
        val desc = getResources().getStringArray(R.array.amino_acids_three)
        for (i in names.indices) {
            this.menuModels.add(MenuModel(names[i], desc[i]))
        }
    }

    private fun getResponse() {
        val retrofitTest = RetrofitClient()
        val retrofit : Retrofit = retrofitTest.getInstance()

        var apiInterface = retrofit.create(ApiInterface::class.java)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getAllUsers()
                if (response.isSuccessful()) {
                    //your code for handaling success response
                    println("BISAAAAjioj")

                } else {

                }
            }catch (Ex:Exception){
                Log.e("Error",Ex.localizedMessage)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_location, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.mRecyclerView)

        populateList()

        val adapter: LocationRVAdapter = LocationRVAdapter(this.menuModels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        getResponse()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}