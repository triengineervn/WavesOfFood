package com.example.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.CartItemBinding
import com.example.wavesoffood.models.CartItem

class CartAdapter(
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val items = mutableListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(data: List<CartItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                cartName.text = item.name
                cartPrice.text = item.price
//                cartImage.setImageResource(item.imageResId!!)

                increaseQuantityBtn.setOnClickListener {
                    item.quantity++
                    cartQuantity.text = item.quantity.toString()
                }

                decreaseQuantityBtn.setOnClickListener {
                    if (item.quantity > 1) {
                        item.quantity--
                        cartQuantity.text = item.quantity.toString()
                    }
                }
                deleteBtn.setOnClickListener {
                    items.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }
}
