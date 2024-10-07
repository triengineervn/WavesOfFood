package com.example.wavesoffood.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapters.MenuAdapter
import com.example.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.example.wavesoffood.models.BaseItem
import com.example.wavesoffood.models.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private var menuItems: MutableList<MenuItem> = mutableListOf()
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

        retrieveMenuItem()


        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef = database.getReference("menu")
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (menuItemSnapshot in dataSnapshot.children) {
                    val menuItem = menuItemSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                val adapter = MenuAdapter(menuItems, requireContext())
                binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.menuRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error retrieving menu data", error.toException())
            }
        })
    }

}