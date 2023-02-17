package com.example.majika

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.LocationRVAdapter
import com.example.majika.viewmodel.LocationViewModel


class Location : Fragment() {

    private val viewModel by lazy { LocationViewModel(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_location, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.mRecyclerView)

        viewModel.apply {
            getLocation()
            locationList.observe(viewLifecycleOwner) {
                val adapter: LocationRVAdapter = LocationRVAdapter(it)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(view.context)
            }
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}