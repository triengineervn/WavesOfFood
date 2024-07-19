package com.example.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.PopularItemBinding
import com.example.wavesoffood.models.PopularItem

class PopularAdapter(
    private val onItemClick: (PopularItem, Int) -> Unit
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    private val items = mutableListOf<PopularItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(data: List<PopularItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: PopularItemBinding,
        private val onItemClick: (PopularItem, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: PopularItem) {
            binding.apply {
                foodName.text = item.name
                foodPrice.text = item.price
                foodImage.setImageResource(item.imageResId)

                root.setOnClickListener { onItemClick(item, adapterPosition) }
            }
        }
    }
}
