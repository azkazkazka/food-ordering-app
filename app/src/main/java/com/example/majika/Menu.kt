package com.example.majika

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.MenuRVAdapter
import com.example.majika.model.MenuModel
import com.example.majika.viewmodel.MenuViewModel


class Menu : Fragment() {

    lateinit var sensorManager : SensorManager
    lateinit var sensor : Sensor
    lateinit var tempText : TextView

    private val viewModel by lazy { MenuViewModel(requireContext()) }

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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)

        tempText = view.findViewById(R.id.tempText)

        val recyclerView: RecyclerView = view.findViewById(R.id.menuRecyclerView)
        viewModel.apply {
            insertCart((activity as MainActivity).updateMenuList)
            getMenu()
            menuList.observe(viewLifecycleOwner) {
                val adapter: MenuRVAdapter = MenuRVAdapter(this@Menu, it)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(view.context)
            }

        }

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