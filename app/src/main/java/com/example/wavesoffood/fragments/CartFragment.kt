package com.example.wavesoffood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.CartAdapter
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.example.wavesoffood.models.CartItem


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartItems = mutableListOf(
            CartItem("Pizza", "$11", R.drawable.menu_photo_1, 1),
            CartItem("Burger", "$8", R.drawable.menu_photo_2, 1),
            CartItem("Pasta", "$12", R.drawable.menu_photo_1, 1),
            CartItem("Chicken", "$11", R.drawable.menu_photo_1, 1),
            CartItem("Beef", "$18", R.drawable.menu_photo_1, 1),
            CartItem("Fish", "$21", R.drawable.menu_photo_1, 1),
            CartItem("Eggs", "$2", R.drawable.menu_photo_1, 1)
        )

        val adapter = CartAdapter()
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        adapter.setData(cartItems)
        return binding.root
    }
}