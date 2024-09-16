package com.example.wavesoffood.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.MenuAdapter
import com.example.wavesoffood.databinding.FragmentSearchBinding
import com.example.wavesoffood.models.MenuItem

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val originalFoodMenu = mutableListOf(
        MenuItem("Pizza", "$11", R.drawable.menu_photo_1),
        MenuItem("Burger", "$8", R.drawable.menu_photo_2),
        MenuItem("Pasta", "$12", R.drawable.menu_photo_1),
        MenuItem("Chicken", "$11", R.drawable.menu_photo_1),
        MenuItem("Beef", "$18", R.drawable.menu_photo_1),
        MenuItem("Fish", "$21", R.drawable.menu_photo_1),
        MenuItem("Eggs", "$2", R.drawable.menu_photo_1)
    )
    private val filteredFoodMenu: MutableList<MenuItem> = originalFoodMenu.toMutableList()

    private lateinit var adapter: MenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = MenuAdapter(filteredFoodMenu)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        setupSearchView()
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filteredFoodMenu.clear()
        filteredFoodMenu.addAll(originalFoodMenu)
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
        val filteredList = originalFoodMenu.filter {
            it.name.contains(query, ignoreCase = true)
        }

        filteredFoodMenu.clear()
        filteredFoodMenu.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }


}