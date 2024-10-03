package com.example.wavesoffood.activities

import CartItem
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    private val foodName: String? by lazy { intent.getStringExtra("MenuItemName") }
    private val foodImage: String? by lazy { intent.getStringExtra("MenuItemImage") }
    private val foodIngredients: String? by lazy { intent.getStringExtra("MenuItemIngredients") }
    private val foodDescription: String? by lazy { intent.getStringExtra("MenuItemDescription") }
    private val foodPrice: String? by lazy { intent.getStringExtra("MenuItemPrice") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        binding.foodName.text = foodName
        Glide.with(this).load(foodImage).into(binding.foodImage)
        binding.foodDescription.text = foodDescription
        binding.foodIngredients.text = foodIngredients
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.addToCartBtn.setOnClickListener {
            if (!foodName.isNullOrEmpty()) {
                addItemToCart(foodName, foodImage, foodDescription, foodIngredients, foodPrice)
            } else {
                showToast("Food name is missing!")
            }
        }
    }

    private fun addItemToCart(
        foodName: String?,
        foodImage: String?,
        foodDescription: String?,
        foodIngredients: String?,
        foodPrice: String?
    ) {
        val userId = auth.currentUser?.uid ?: return showToast("User not authenticated")
        val cartItem = CartItem(foodName, foodPrice, foodImage, foodDescription, foodIngredients, 1)
        val database = getUserCartReference(userId)

        database.orderByChild("name").equalTo(foodName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        updateExistingCartItem(snapshot)
                    } else {
                        addNewCartItem(database, cartItem)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showToast("Error occurred: ${error.message}")
                }
            })
    }

    private fun getUserCartReference(userId: String): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("users").child(userId).child("cart")
    }

    private fun updateExistingCartItem(snapshot: DataSnapshot) {
        for (data in snapshot.children) {
            val existingItem = data.getValue(CartItem::class.java)
            existingItem?.let {
                val newQuantity = it.quantity!! + 1
                data.ref.child("quantity").setValue(newQuantity)
                    .addOnSuccessListener { showToast("Quantity updated successfully"); finish() }
                    .addOnFailureListener { showToast("Failed to update quantity") }
            }
        }
    }

    private fun addNewCartItem(database: DatabaseReference, cartItem: CartItem) {
        database.push().setValue(cartItem)
            .addOnSuccessListener { showToast("Item added to cart"); finish() }
            .addOnFailureListener { showToast("Failed to add item") }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
