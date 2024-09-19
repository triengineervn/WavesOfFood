package com.example.wavesoffood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.NotificationAdapter
import com.example.wavesoffood.databinding.FragmentNotificationBottomBinding
import com.example.wavesoffood.models.MenuItem
import com.example.wavesoffood.models.NotificationItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomBinding.inflate(inflater, container, false)
        val notifications = mutableListOf(
            NotificationItem("Your Order has been Canceled Successfully", R.drawable.sad_emoji),
            NotificationItem("Order has been taken by the driver", R.drawable.delivery_green),
            NotificationItem("Congrats Your Order Placed", R.drawable.order_success_illustration)
        )

        val adapter = NotificationAdapter(notifications)
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter
        return binding.root
    }

}