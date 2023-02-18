package com.example.majika

import android.content.Context.SENSOR_SERVICE
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.MenuRVAdapter
import com.example.majika.model.MenuModel
import com.example.majika.viewmodel.MenuViewModel


class Menu : Fragment(), SensorEventListener {

    lateinit var sensorManager : SensorManager
    lateinit var sensor : Sensor
    lateinit var tempText : TextView
    lateinit var searchView: SearchView
    lateinit var adapter: MenuRVAdapter
    var filteredList: ArrayList<MenuModel> = ArrayList<MenuModel>()
    var menuNow: List<MenuModel> = ArrayList<MenuModel>()

    val viewModel by lazy { MenuViewModel(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = this.activity?.getSystemService(SENSOR_SERVICE) as SensorManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }
        if (sensor == null) {
            Toast.makeText(this?.activity, "Your device does not support temperature sensor!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("updatemenulist", (activity as MainActivity).updateMenuList)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            (activity as MainActivity).updateMenuList =
                savedInstanceState.getSerializable("updatemenulist") as ArrayList<MenuModel>;
        } else {
            // no data to retrieve
        }

        val orientation = resources.configuration.orientation
        val layoutId = if (orientation == Configuration.ORIENTATION_PORTRAIT) R.layout.fragment_menu_portrait else R.layout.fragment_menu_landscape
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(layoutId, container, false)

        tempText = view.findViewById(R.id.tempText)


        val recyclerView: RecyclerView = view.findViewById(R.id.menuRecyclerView)
//        lateinit var adapter: MenuRVAdapter
        viewModel.apply {
            insertCart((activity as MainActivity).updateMenuList)
            getMenu()
            menuList.observe(viewLifecycleOwner) {
                menuNow = it
                if(filteredList.isEmpty()){
                    adapter = MenuRVAdapter(this@Menu, it)
                }
                else{
                    adapter = MenuRVAdapter(this@Menu, filteredList)
                }
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(view.context)
            }
        }

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                println("msg: $msg")
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filteredList = ArrayList()

                for (item in menuNow) {
                    println(item.get_name.lowercase())
                    if (item.get_name.lowercase().contains(msg.lowercase())) {
                        filteredList.add(item)
                    }
                }
                if (filteredList.isEmpty()) {
//                    Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
                } else {
                    println("filteredlist: $filteredList")
                    adapter.filterList(filteredList)
                }
                return false
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var temp : Float? = event?.values?.get(0)
        tempText.text = temp.toString() + "°C"

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}