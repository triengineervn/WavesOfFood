package com.example.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.HistoryOrderItemBinding
import com.example.wavesoffood.helpers.xhelper
import com.example.wavesoffood.models.OrdersModel
import com.google.firebase.database.FirebaseDatabase

class HistoryOrderAdapter(
    private val listOfOrders: MutableList<OrdersModel>,
    private val onClickItemListener: OnClickListener
) :
    RecyclerView.Adapter<HistoryOrderAdapter.HistoryOrderViewHolder>() {
    inner class HistoryOrderViewHolder(
        private var binding: HistoryOrderItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var database: FirebaseDatabase
        private lateinit var userId: String

        fun bind(position: Int) {

            database = FirebaseDatabase.getInstance()
            userId = listOfOrders[position].userId!!

            binding.apply {
                orderName.text = listOfOrders[position].cartItems!!.first().name
                totalPrice.text = listOfOrders[position].totalPrice + "$"
                timeOrder.text = xhelper.formatTime(listOfOrders[position].currentTime!!.toLong())
                locationOrder.text = listOfOrders[position].address

                getOrderStatus(position)



                payBtn.setOnClickListener {
                    if (!listOfOrders[position].paymentReceived) {
                        updatePaymentMethod()
                    }
                }

                root.setOnClickListener {
                    onClickItemListener.onClickItemListener(position)
                }
            }
        }

        private fun updatePaymentMethod() {
            val itemPushKey = listOfOrders.firstOrNull()?.itemPushKey
            val completedOrderRef =
                database.reference.child("users/${userId}/completedOrders/${itemPushKey}")
            completedOrderRef.child("paymentReceived").setValue(true)
            val historyOrderRef =
                database.reference.child("users/${userId}/history/${itemPushKey}")
            historyOrderRef.child("paymentReceived").setValue(true)
            binding.payBtn.text = "Paid"
            Toast.makeText(binding.root.context, "The order was paid", Toast.LENGTH_SHORT).show()
        }

        private fun getOrderStatus(position: Int) {
            when (listOfOrders[position].orderAccepted) {
                true -> {
                    binding.payBtn.isEnabled = true
                    binding.payBtn.setBackgroundResource(R.drawable.background_primary_btn_s)

                }

                false -> {
                    binding.payBtn.isEnabled = false
                    binding.payBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.divider
                        )
                    )
                }
            }

            if (listOfOrders[position].paymentReceived) {
                binding.payBtn.text = "Paid"
            }
        }

    }

    interface OnClickListener {
        fun onClickItemListener(position: Int)
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