package com.example.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.IngredientItemBinding
import com.example.wavesoffood.models.IngredientItem

class IngredientAdapter(
    private val foodName: String?,
    private val foodImage: Int,
    private val ingredientItems: MutableList<IngredientItem>
) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    inner class IngredientViewHolder(private var binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                ingredientItem.text = "• ${ingredientItems[position].name}"

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientAdapter.IngredientViewHolder {
        val binding =
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientAdapter.IngredientViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = ingredientItems.size
}