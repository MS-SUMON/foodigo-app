package com.example.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.user.R
import com.example.user.databinding.MenuItemBinding
import com.example.user.modelpackageforuser.MenuModel

class SellerMenuAdapter(
    private val context: Context,
    private val menuList: ArrayList<MenuModel>
) : RecyclerView.Adapter<SellerMenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]

        holder.binding.menuFoodName.text = item.foodName
        holder.binding.ownerNamePopular.text = item.ownerName
        holder.binding.menuPrice.text = item.price

        // Load image from drawable
        val imageResId = context.resources.getIdentifier(
            item.imageName, "drawable", context.packageName
        )
        if (imageResId != 0) {
            holder.binding.menuImage.setImageResource(imageResId)
        } else {
            holder.binding.menuImage.setImageResource(R.drawable.hotpot_2) // default
        }

        // Add to cart click
        holder.binding.menuAddToCart.setOnClickListener {
            // Placeholder for cart logic
        }
    }

    override fun getItemCount(): Int = menuList.size
}
