package com.example.wavesoffood.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.activities.DetailsActivity
import com.example.wavesoffood.databinding.MenuItemBinding
import com.example.wavesoffood.models.MenuItem

class MenuAdapter(
    private val menuItems: MutableList<MenuItem>,
    private val requireContext: Context
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    inner class MenuViewHolder(private var binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

            }
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

fun View.OnClickListener?.onClick(position: Int) {

}
