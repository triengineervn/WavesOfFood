package com.example.wavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.CartItemBinding
import com.example.wavesoffood.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId: String = auth.currentUser?.uid.orEmpty()
    private val cartItemReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("users").child(userId).child("cart")

    init {
        itemQuantity = IntArray(cartItems.size) { 1 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position], position)
    }

    override fun getItemCount(): Int = cartItems.size

    fun setData(data: List<CartItem>) {
        cartItems.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    companion object {
        private var itemQuantity: IntArray = intArrayOf()
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem, position: Int) {
            with(binding) {
                cartName.text = cartItem.name
                cartPrice.text = cartItem.price
                cartItem.image?.let { loadImage(it) }
                cartQuantity.text = cartItem.quantity.toString()

                setupListeners(position, cartItem)
            }
        }

        private fun loadImage(imageUri: String) {
            val uri = Uri.parse(imageUri)
            Glide.with(context).load(uri).into(binding.cartImage)
        }

        private fun setupListeners(position: Int, cartItem: CartItem) {
            binding.apply {
                increaseQuantityBtn.setOnClickListener {
                    increaseQuantity(position, cartItem)
                }
                decreaseQuantityBtn.setOnClickListener {
                    decreaseQuantity(position, cartItem)
                }
                deleteBtn.setOnClickListener {
                    deleteItemFromCart(position)
                }
            }
        }

        private fun increaseQuantity(position: Int, cartItem: CartItem) {
            cartItem.quantity = cartItem.quantity!! + 1
            binding.cartQuantity.text = cartItem.quantity.toString()
            updateQuantityInDatabase(position, cartItem)
        }

        private fun decreaseQuantity(position: Int, cartItem: CartItem) {
            if (cartItem.quantity!! > 1) {
                cartItem.quantity = cartItem.quantity!! - 1
                binding.cartQuantity.text = cartItem.quantity.toString()
                updateQuantityInDatabase(position, cartItem)
            }
        }

        private fun updateQuantityInDatabase(position: Int, cartItem: CartItem) {
            getUniqueKeyAtPosition(position) { uniqueKey ->
                if (uniqueKey != null) {
                    cartItemReference.child(uniqueKey).child("quantity").setValue(cartItem.quantity)
                        .addOnSuccessListener {
                            // Có thể thông báo thành công nếu cần
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update quantity", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
        }

        private fun deleteItemFromCart(position: Int) {
            getUniqueKeyAtPosition(position) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            cartItemReference.child(uniqueKey).removeValue()
                .addOnSuccessListener {
                    updateCartAfterRemoval(position)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show()
                }
        }

        private fun updateCartAfterRemoval(position: Int) {
            cartItems.removeAt(position)
            itemQuantity = itemQuantity.filterIndexed { index, _ -> index != position }.toIntArray()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }

        private fun getUniqueKeyAtPosition(
            positionRetrieved: Int,
            onCompleteListener: (String?) -> Unit
        ) {
            cartItemReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val uniqueKey = snapshot.children.elementAtOrNull(positionRetrieved)?.key
                    onCompleteListener(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}
