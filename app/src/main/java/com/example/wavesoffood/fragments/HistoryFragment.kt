package com.example.wavesoffood.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wavesoffood.R
import com.example.wavesoffood.activities.HistoryOrdersActivity
import com.example.wavesoffood.adapters.ReBuyAdapter
import com.example.wavesoffood.databinding.FragmentHistoryBinding
import com.example.wavesoffood.models.OrdersModel
import com.example.wavesoffood.models.ReBuyItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {
    private lateinit var reBuyAdapter: ReBuyAdapter
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrders: MutableList<OrdersModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        retrieveHistoryOrders()

        binding.reBuyItem.setOnClickListener {
            goReBuyItem()
        }


        return binding.root
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
        Toast.makeText(requireContext(), "The order was paid", Toast.LENGTH_SHORT).show()
    }

    private fun goReBuyItem() {
        val intent = Intent(requireContext(), HistoryOrdersActivity::class.java)
        intent.putParcelableArrayListExtra("listOfOrders", ArrayList(listOfOrders))
        startActivity(intent)
    }

    private fun retrieveHistoryOrders() {
        binding.reBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid!!

        val buyRef = database.reference.child("users").child(userId).child("history")
        val shortingQuery = buyRef.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistory = buySnapshot.getValue(OrdersModel::class.java)
                    buyHistory?.let {
                        listOfOrders.add(it)
                    }
                }
                listOfOrders.reverse()
                if (listOfOrders.isNotEmpty()) {
                    setDataInReBuyItem()
                    setDataInReBuyRecyclerView()
                }
            }

            private fun setDataInReBuyItem() {
                binding.reBuyItem.visibility = View.VISIBLE
                val reBuyItem = listOfOrders.firstOrNull()

                reBuyItem.let {
                    with(binding) {
                        val firstCartItem = it?.cartItems?.firstOrNull()
                        if (firstCartItem != null) {
                            foodName.text = firstCartItem.name
                            foodPrice.text = firstCartItem.price
                            val uri = Uri.parse(firstCartItem.image)
                            Glide.with(requireContext()).load(uri).into(foodImage)
                        }

                    }
                }
                val isOrderIsAccepted = listOfOrders[0].orderAccepted
                val isPaid = listOfOrders[0].paymentReceived
                if (!isOrderIsAccepted) {
                    binding.payBtn.isEnabled = false
                    binding.payBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.divider
                        )
                    )
                    binding.payBtn.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.background
                        )
                    )
                } else {
                    binding.payBtn.isEnabled = true
                    binding.payBtn.setBackgroundResource(R.drawable.background_primary_btn_s)
                }


                binding.payBtn.setOnClickListener {
                    updatePaymentMethod()
                }

                if (isPaid) {
                    binding.payBtn.text = "Paid"
                }

            }

            private fun setDataInReBuyRecyclerView() {
                val reBuyItems = mutableSetOf<ReBuyItem>()

                for (order in listOfOrders) {
                    order.cartItems?.forEach { cartItem ->
                        val newItem = ReBuyItem(cartItem.name!!, cartItem.price!!, cartItem.image!!)
                        reBuyItems.add(newItem)
                    }
                }

                val uniqueReBuyItems = reBuyItems.toMutableList()
                reBuyAdapter = ReBuyAdapter(uniqueReBuyItems, requireContext())
                binding.reBuyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.reBuyRecyclerView.adapter = reBuyAdapter
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}