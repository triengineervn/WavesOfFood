package com.example.wavesoffood.activities

import CartItem
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapters.ReBuyAdapter

import com.example.wavesoffood.databinding.ActivityReBuyItemDetailsBinding
import com.example.wavesoffood.models.OrdersModel
import com.example.wavesoffood.models.ReBuyItem

class ReBuyItemDetailsActivity : AppCompatActivity() {
    private val binding: ActivityReBuyItemDetailsBinding by lazy {
        ActivityReBuyItemDetailsBinding.inflate(layoutInflater)
    }
    private var listOfOrders: MutableList<ReBuyItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val itemOrder =
            intent.getParcelableArrayListExtra<CartItem>("listOfOrder")

        itemOrder?.forEach {
            val newItem = ReBuyItem(it.name!!, it.price!!, it.image!!)
            listOfOrders.add(newItem)
        }


        setAdapters(listOfOrders)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setAdapters(listOfOrders: MutableList<ReBuyItem>) {
        val recyclerView = binding.reBuyRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ReBuyAdapter(listOfOrders, this)
        binding.reBuyRecyclerView.adapter = adapter
    }
}