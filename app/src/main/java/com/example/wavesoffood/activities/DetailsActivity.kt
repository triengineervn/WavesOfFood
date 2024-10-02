package com.example.wavesoffood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getStringExtra("MenuItemImage")
        val foodIngredients = intent.getStringExtra("MenuItemIngredients")
        val foodDescription = intent.getStringExtra("MenuItemDescription")
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.foodName.text = foodName
        Glide.with(this).load(foodImage).into(binding.foodImage)
        binding.foodDescription.text = foodDescription
        binding.foodIngredients.text = foodIngredients
    }
}