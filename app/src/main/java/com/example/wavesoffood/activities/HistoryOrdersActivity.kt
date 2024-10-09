package com.example.wavesoffood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapters.HistoryOrderAdapter
import com.example.wavesoffood.databinding.ActivityHistoryOrdersBinding
import com.example.wavesoffood.models.OrdersModel
import java.util.ArrayList


class HistoryOrdersActivity : AppCompatActivity(), HistoryOrderAdapter.OnClickListener {

    private val binding: ActivityHistoryOrdersBinding by lazy {
        ActivityHistoryOrdersBinding.inflate(layoutInflater)
    }

    private var listOfOrders: MutableList<OrdersModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listOfOrders =
            intent.getParcelableArrayListExtra<OrdersModel>("listOfOrders")!!.toMutableList()

        setAdapters(listOfOrders)

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun setAdapters(listOfOrders: MutableList<OrdersModel>) {
        val recyclerView = binding.historyOrdersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = HistoryOrderAdapter(listOfOrders, this)
        binding.historyOrdersRecyclerView.adapter = adapter
    }

    override fun onClickItemListener(position: Int) {
        val intent = Intent(this, ReBuyItemDetailsActivity::class.java)
        intent.putParcelableArrayListExtra(
            "listOfOrder",
            ArrayList(listOfOrders[position].cartItems!!)
        )
        startActivity(intent)

    }

}