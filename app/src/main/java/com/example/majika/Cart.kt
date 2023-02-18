package com.example.majika


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.adapter.CartRVAdapter
import com.example.majika.viewmodel.CartViewModel


class Cart : Fragment() {

    val viewModel by lazy { CartViewModel(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)
        val buttonBayar : Button = view.findViewById(R.id.bayarButton)
        buttonBayar.setOnClickListener {
            val intent : Intent = Intent(activity, Payment::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.cartRecyclerView)
        viewModel.apply {
            insertCart((activity as MainActivity).updateMenuList)
            getCart()
            cartList.observe(viewLifecycleOwner) {
                val adapter: CartRVAdapter = CartRVAdapter(this@Cart, it)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(view.context)
            }
            getPrice()
            totalPrice.observe(viewLifecycleOwner){
                var priceTotal: TextView = view.findViewById(R.id.totalPrice)
                priceTotal.text = "Rp $it"
            }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}