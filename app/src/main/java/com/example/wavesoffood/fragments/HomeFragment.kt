package com.example.wavesoffood.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.MenuAdapter
import com.example.wavesoffood.databinding.FragmentHomeBinding
import com.example.wavesoffood.models.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private val menuItems = mutableListOf<MenuItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupMenuButton()
        setupImageSlider()
        retrieveAndDisplayPopularItems()
        return binding.root
    }

    private fun setupMenuButton() {
        binding.viewMenuBtn.setOnClickListener {
            val bottomSheetDialog = MenuBottomFragment()
            bottomSheetDialog.show(parentFragmentManager, "MenuBottomFragment")
        }
    }

    private fun setupImageSlider() {
        val imageList = listOf(
            SlideModel(R.drawable.banner1, ScaleTypes.FIT),
            SlideModel(R.drawable.banner2, ScaleTypes.FIT),
            SlideModel(R.drawable.banner3, ScaleTypes.FIT)
        )

        binding.imageSlider.apply {
            setImageList(imageList, ScaleTypes.FIT)
            setItemClickListener(object : ItemClickListener {
                override fun doubleClick(position: Int) { /* Do nothing */
                }

                override fun onItemSelected(position: Int) {
                    Toast.makeText(requireContext(), "Selected Image $position", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun retrieveAndDisplayPopularItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef = database.getReference("menu")

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                menuItems.clear()

                dataSnapshot.children.forEach { menuItemSnapshot ->
                    menuItemSnapshot.getValue(MenuItem::class.java)?.let { menuItems.add(it) }
                }

                displayPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error retrieving menu data", error.toException())
            }
        })
    }

    private fun displayPopularItems() {
        if (menuItems.isNotEmpty()) {
            val randomItems = menuItems.shuffled().take(3)  // Display 3 random popular items
            setPopularItemAdapter(randomItems)
        } else {
            Log.i("HomeFragment", "No menu items found.")
        }
    }

    private fun setPopularItemAdapter(subsetMenuItems: List<MenuItem>) {
        val popularAdapter = MenuAdapter(subsetMenuItems.toMutableList(), requireContext())
        binding.popularRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularAdapter
        }
    }
}
