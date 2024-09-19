package com.example.wavesoffood.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        private val itemOnClickListener: OnClickListener? = null

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemOnClickListener?.onClick(position)
                }
                //--selector listener on click detail!!!
                val intent = Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", menuItems[position].name)
                intent.putExtra("MenuItemImage", menuItems[position].imageResId)
                requireContext.startActivity(intent)
            }
        }

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

fun View.OnClickListener?.onClick(position: Int) {

}
