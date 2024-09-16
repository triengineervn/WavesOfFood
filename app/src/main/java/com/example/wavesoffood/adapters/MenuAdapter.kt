package com.example.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.MenuItemBinding
import com.example.wavesoffood.models.MenuItem

class MenuAdapter(private val menuItems: MutableList<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    inner class MenuViewHolder(private var binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = menuItems[position].name
                foodPrice.text = menuItems[position].price
                foodImage.setImageResource(menuItems[position].imageResId)
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