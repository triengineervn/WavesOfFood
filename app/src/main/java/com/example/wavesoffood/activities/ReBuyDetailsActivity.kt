package com.example.wavesoffood.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapters.ReBuyAdapter
import com.example.wavesoffood.databinding.ActivityReBuyDetailsBinding
import com.example.wavesoffood.models.OrdersModel
import com.example.wavesoffood.models.ReBuyItem


class ReBuyDetailsActivity : AppCompatActivity() {

    private val binding: ActivityReBuyDetailsBinding by lazy {
        ActivityReBuyDetailsBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val listOfOrders = intent.getParcelableArrayListExtra<OrdersModel>("listOfOrders")
        val reBuyItems = mutableSetOf<ReBuyItem>()
        listOfOrders?.let { orders ->
            if (orders.isNotEmpty()) {
                for (order in orders) {
                    order.cartItems?.forEach { cartItem ->
                        val newItem = ReBuyItem(
                            cartItem.name!!,
                            "${cartItem.price!!} $", cartItem.image!!
                        )
                        reBuyItems.add(newItem)
                    }
                }
                val uniqueReBuyItems = reBuyItems.toMutableList()
                setAdapters(uniqueReBuyItems, this)
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun setAdapters(reBuyItems: MutableList<ReBuyItem>, context: Context) {
        val recyclerView = binding.reBuyRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ReBuyAdapter(reBuyItems, context)
        binding.reBuyRecyclerView.adapter = adapter
    }
}