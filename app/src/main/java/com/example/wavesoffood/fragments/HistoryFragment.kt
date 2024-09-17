package com.example.wavesoffood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.ReBuyAdapter
import com.example.wavesoffood.databinding.FragmentHistoryBinding
import com.example.wavesoffood.databinding.ReBuyItemBinding
import com.example.wavesoffood.models.CartItem
import com.example.wavesoffood.models.ReBuyItem


class HistoryFragment : Fragment() {
    private lateinit var reBuyAdapter: ReBuyAdapter
    private lateinit var binding: FragmentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val reBuyItems = mutableListOf(
            ReBuyItem("Pizza", "$11", R.drawable.menu_photo_1),
            ReBuyItem("Burger", "$8", R.drawable.menu_photo_2),
            ReBuyItem("Pasta", "$12", R.drawable.menu_photo_1),
            ReBuyItem("Chicken", "$11", R.drawable.menu_photo_1),
            ReBuyItem("Beef", "$18", R.drawable.menu_photo_1),
            ReBuyItem("Fish", "$21", R.drawable.menu_photo_1),
            ReBuyItem("Eggs", "$2", R.drawable.menu_photo_1)
        )

        reBuyAdapter = ReBuyAdapter(reBuyItems)
        binding.ReBuyRecyclerView.adapter = reBuyAdapter
        binding.ReBuyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


}