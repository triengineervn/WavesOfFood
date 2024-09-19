package com.example.wavesoffood.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapters.CartAdapter
import com.example.wavesoffood.adapters.IngredientAdapter
import com.example.wavesoffood.databinding.ActivityDetailsBinding
import com.example.wavesoffood.models.CartItem
import com.example.wavesoffood.models.IngredientItem

class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val ingredientItem = mutableListOf(
            IngredientItem("Tomato"),
            IngredientItem("Tomato"),
            IngredientItem("Tomato"),
        )
        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getIntExtra("MenuItemImage", 0)
        val adapter = IngredientAdapter(foodName, foodImage, ingredientItem)
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ingredientRecyclerView.adapter = adapter
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.foodName.text = foodName
        binding.foodImage.setImageResource(foodImage)
    }
}