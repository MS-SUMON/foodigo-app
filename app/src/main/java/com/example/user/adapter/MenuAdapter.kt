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
    private val ownerNames: MutableList<String>, // ADDED: Assuming you have a list of owner names for consistency
    private val context: Context // RENAMED: Changed 'requireContext' to 'context' for clarity in adapter
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // Removed the unused itemClickListener interface and property

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
                // Bind the data to the layout views
                menuFoodName.text = menuItemsName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuImages[position])
                // ownerNamePopular is the ID in your XML for the owner name
                ownerNamePopular.text = ownerNames[position]

                // --- FIX: Implement the click listener here ---
                // This correctly uses the item's view and its context.
                binding.root.setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java).apply {
                        // Pass ALL relevant data using consistent keys
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