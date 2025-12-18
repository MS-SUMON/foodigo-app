package com.example.user.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.user.DetailsActivity
import com.example.user.databinding.MenuItemBinding


class MenuAdapter(
    private val menuItemsName: MutableList<String>,
    private val menuItemPrice: MutableList<String>,
    private val menuImages: MutableList<Int>,
    private val ownerNames: MutableList<String>,
    private val context: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemsName.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {

                menuFoodName.text = menuItemsName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuImages[position])
                ownerName.text = ownerNames[position]

                binding.root.setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java).apply {
                        putExtra("menuItemName", menuItemsName[position])
                        putExtra("menuItemPrice", menuItemPrice[position])
                        putExtra("menuItemImage", menuImages[position])
                        putExtra("menuItemOwner", ownerNames[position])
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}