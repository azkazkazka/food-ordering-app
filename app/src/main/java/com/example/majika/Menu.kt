package com.example.majika

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.MenuRVAdapter
import com.example.majika.viewmodel.MenuViewModel


class Menu() : Fragment() {

    private val viewModel by lazy { MenuViewModel(requireContext()) }

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
}