package com.example.wavesoffood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.activities.PaymentActivity
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
            CartItem("Pizza", "$11", "", 1),
            CartItem("Burger", "$8", "", 1),
            CartItem("Pasta", "$12", "", 1),
            CartItem("Chicken", "$11", "", 1),
            CartItem("Beef", "$18", "", 1),
            CartItem("Fish", "$21", "", 1),
            CartItem("Eggs", "$2", "", 1)
        )

        binding.proceedBtn.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            startActivity(intent)

        }

        val adapter = CartAdapter()
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        adapter.setData(cartItems)
        return binding.root
    }
}