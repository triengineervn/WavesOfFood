package com.example.wavesoffood.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapters.HistoryOrderAdapter
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

        setAdapters(listOfOrders!!)

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun setAdapters(listOfOrders: MutableList<OrdersModel>) {
        val recyclerView = binding.historyOrdersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = HistoryOrderAdapter(listOfOrders)
        binding.historyOrdersRecyclerView.adapter = adapter
    }
}