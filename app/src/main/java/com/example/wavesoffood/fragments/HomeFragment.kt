package com.example.wavesoffood.fragments

import android.content.Intent
import android.os.Bundle
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
import com.example.wavesoffood.activities.DetailsActivity
import com.example.wavesoffood.activities.PaymentActivity
import com.example.wavesoffood.adapters.PopularAdapter
import com.example.wavesoffood.databinding.FragmentHomeBinding
import com.example.wavesoffood.models.PopularItem


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewMenuBtn.setOnClickListener {
            val bottomSheetDialog = MenuBottomFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }

        })


        val popularItems = listOf(
            PopularItem("Pizza", "$10", R.drawable.menu_photo_1),
            PopularItem("Burger", "$8", R.drawable.menu_photo_2),
            PopularItem("Pasta", "$12", R.drawable.menu_photo_3),
            PopularItem("Chicken", "$10", R.drawable.menu_photo_1),
        )

        val adapter = PopularAdapter(
            { item, _ ->
                Toast.makeText(context, "Go to detail ${item.name}", Toast.LENGTH_SHORT).show()
            }, requireContext()
        )
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
        adapter.setData(popularItems)
    }
}