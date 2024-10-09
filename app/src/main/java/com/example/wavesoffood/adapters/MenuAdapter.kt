package com.example.wavesoffood.adapters

import CartItem
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.activities.DetailsActivity
import com.example.wavesoffood.databinding.MenuItemBinding
import com.example.wavesoffood.models.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuAdapter(
    private val menuItems: MutableList<MenuItem>,
    private val requireContext: Context
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    inner class MenuViewHolder(private var binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var auth: FirebaseAuth = FirebaseAuth.getInstance()

        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItems = menuItems[position]
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItems.name)
                putExtra("MenuItemImage", menuItems.image)
                putExtra("MenuItemDescription", menuItems.description)
                putExtra("MenuItemIngredients", menuItems.ingredients)
                putExtra("MenuItemPrice", menuItems.price)
            }

            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItems = menuItems[position]
            binding.apply {
                foodName.text = menuItems.name
                foodPrice.text = menuItems.price
                val uri = Uri.parse(menuItems.image)
                Glide.with(requireContext).load(uri).into(foodImage)

                addToCartBtn.setOnClickListener {
                    addItemToCart(menuItems)
                }
            }
        }

        private fun addItemToCart(menuItems: MenuItem) {
            val userId = auth.currentUser?.uid ?: ""
            val database =
                FirebaseDatabase.getInstance().reference.child("users").child(userId).child("cart")
            val cartItem =
                CartItem(
                    menuItems.name,
                    menuItems.price,
                    menuItems.image,
                    menuItems.description,
                    menuItems.ingredients,
                    1
                )
            // save data to database
            database.orderByChild("name").equalTo(menuItems.name)
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
                                                requireContext,
                                                "Quantity updated successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                requireContext,
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
                                        requireContext,
                                        "Item added to cart",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        requireContext,
                                        "Failed to add item",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            requireContext,
                            "Error occurred: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size
}
