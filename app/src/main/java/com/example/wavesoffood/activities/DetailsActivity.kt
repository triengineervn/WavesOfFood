package com.example.wavesoffood.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ActivityDetailsBinding
import com.example.wavesoffood.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodIngredients: String? = null
    private var foodDescription: String? = null
    private var foodPrice: String? = null

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("MenuItemName")
        foodImage = intent.getStringExtra("MenuItemImage")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.foodName.text = foodName
        Glide.with(this).load(foodImage).into(binding.foodImage)
        binding.foodDescription.text = foodDescription
        binding.foodIngredients.text = foodIngredients

        binding.addToCartBtn.setOnClickListener {
            addItemToCart(foodName, foodImage, foodDescription, foodIngredients, foodPrice)
        }
    }

    private fun addItemToCart(
        foodName: String?,
        foodImage: String?,
        foodDescription: String?,
        foodIngredients: String?,
        foodPrice: String?
    ) {
        val userId = auth.currentUser?.uid ?: ""
        val database =
            FirebaseDatabase.getInstance().reference.child("users").child(userId).child("cart")
        val cartItem =
            CartItem(
                foodName,
                foodPrice,
                foodImage,
                foodDescription,
                foodIngredients,
                1
            )
        // save data to database
        database.orderByChild("name").equalTo(foodName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (data in snapshot.children) {
                            val existingItem = data.getValue(CartItem::class.java)
                            if (existingItem != null) {
                                val newQuantity = existingItem.quantity!! + 1
                                data.ref.child("quantity").setValue(newQuantity)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            this@DetailsActivity,
                                            "Quantity updated successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this@DetailsActivity,
                                            "Failed to update quantity",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    } else {
                        // Nếu không tồn tại thì thêm sản phẩm mới
                        val newCartItemRef = database.push()
                        newCartItemRef.setValue(cartItem)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@DetailsActivity,
                                    "Item added to cart",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this@DetailsActivity,
                                    "Failed to add item",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Error occurred: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })


    }
}