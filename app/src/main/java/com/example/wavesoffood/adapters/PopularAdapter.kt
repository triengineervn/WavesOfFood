package com.example.wavesoffood.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.activities.DetailsActivity
import com.example.wavesoffood.databinding.PopularItemBinding
import com.example.wavesoffood.models.PopularItem

class PopularAdapter(
    private val onItemClick: (PopularItem, Int) -> Unit,
    private val requireContext: Context

) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    private val popularItems = mutableListOf<PopularItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(popularItems[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", popularItems[position].name)
            intent.putExtra("MenuItemImage", popularItems[position].imageResId)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount() = popularItems.size

    fun setData(data: List<PopularItem>) {
        popularItems.clear()
        popularItems.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: PopularItemBinding,
        private val onItemClick: (PopularItem, Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(popularItems: PopularItem) {
            binding.apply {
                foodName.text = popularItems.name
                foodPrice.text = popularItems.price
                foodImage.setImageResource(popularItems.imageResId)

                root.setOnClickListener {
                    onItemClick(popularItems, adapterPosition)
                }
            }
        }
    }
}

