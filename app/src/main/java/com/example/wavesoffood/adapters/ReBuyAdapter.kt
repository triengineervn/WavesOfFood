package com.example.wavesoffood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ReBuyItemBinding
import com.example.wavesoffood.models.ReBuyItem

class ReBuyAdapter(
    private val reBuyItem: MutableList<ReBuyItem>,
    private var requireContext: Context
) :
    RecyclerView.Adapter<ReBuyAdapter.ReBuyViewHolder>() {
    class ReBuyViewHolder(private val binding: ReBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reBuyItem: ReBuyItem, requireContext: Context) {
            binding.foodName.text = reBuyItem.name
            binding.foodPrice.text = reBuyItem.price
            Glide.with(requireContext).load(reBuyItem.image).into(binding.foodImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReBuyViewHolder {
        var binding = ReBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReBuyViewHolder(binding)
    }

    override fun getItemCount(): Int = reBuyItem.size

    override

    fun onBindViewHolder(holder: ReBuyViewHolder, position: Int) {
        holder.bind(reBuyItem[position], requireContext)
    }
}