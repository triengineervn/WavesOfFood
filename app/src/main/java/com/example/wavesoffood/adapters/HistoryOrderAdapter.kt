package com.example.wavesoffood.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.HistoryOrderItemBinding
import com.example.wavesoffood.helpers.xhelper
import com.example.wavesoffood.models.OrdersModel

class HistoryOrderAdapter(private val listOfOrders: MutableList<OrdersModel>) :
    RecyclerView.Adapter<HistoryOrderAdapter.HistoryOrderViewHolder>() {
    inner class HistoryOrderViewHolder(
        private var binding: HistoryOrderItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                orderName.text = listOfOrders[position].cartItems!!.first().name
                totalPrice.text = listOfOrders[position].totalPrice
                timeOrder.text = xhelper.formatTime(listOfOrders[position].currentTime!!.toLong())
                locationOrder.text = listOfOrders[position].address

                payBtn.text = when (listOfOrders[position].paymentReceived) {
                    true -> "Paid"
                    false -> "Pay"
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryOrderViewHolder {
        val binding =
            HistoryOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = listOfOrders.size


}