package com.example.majika

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class Location : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView: RecyclerView = findViewById(R.id.mRecyclerView)

        populateList()

        val adapter: LocationRVAdapter = LocationRVAdapter(this.menuModels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        getResponse()
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
                    Toast.makeText(
                        this@MainActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
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
        return inflater.inflate(R.layout.fragment_location, container, false)
    }
}