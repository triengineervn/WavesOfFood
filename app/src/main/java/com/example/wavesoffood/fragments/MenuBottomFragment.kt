package com.example.wavesoffood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.MenuAdapter
import com.example.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.example.wavesoffood.models.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            dismiss()
        }

        val menuItems = mutableListOf(
            MenuItem("Pizza", "$11", R.drawable.menu_photo_1),
            MenuItem("Burger", "$8", R.drawable.menu_photo_2),
            MenuItem("Pasta", "$12", R.drawable.menu_photo_1),
            MenuItem("Chicken", "$11", R.drawable.menu_photo_1),
            MenuItem("Beef", "$18", R.drawable.menu_photo_1),
            MenuItem("Fish", "$21", R.drawable.menu_photo_1),
            MenuItem("Eggs", "$2", R.drawable.menu_photo_1)
        )

        val adapter = MenuAdapter(menuItems)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

}