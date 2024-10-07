package com.example.wavesoffood.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapters.MenuAdapter
import com.example.wavesoffood.databinding.FragmentSearchBinding
import com.example.wavesoffood.models.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var database: FirebaseDatabase
    private val menuItems = mutableListOf<MenuItem>()
    private val filteredFoodMenu: MutableList<MenuItem> = menuItems.toMutableList()

    private lateinit var adapter: MenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
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
                setupSearchView()
                showAllMenu()
            }

            private fun setAdapter() {

                adapter = MenuAdapter(filteredFoodMenu, requireContext())
                binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.menuRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error retrieving menu data", error.toException())
            }

            private fun showAllMenu() {
                filteredFoodMenu.clear()
                filteredFoodMenu.addAll(menuItems)
                adapter.notifyDataSetChanged()
            }

            private fun setupSearchView() {
                binding.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        filterMenuItems(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        filterMenuItems(newText)
                        return true
                    }
                })
            }

            private fun filterMenuItems(query: String) {
                val filteredList = menuItems.filter {
                    it.name!!.contains(query, ignoreCase = true)
                }

                filteredFoodMenu.clear()
                filteredFoodMenu.addAll(filteredList)
                adapter.notifyDataSetChanged()
            }
        })
    }


}