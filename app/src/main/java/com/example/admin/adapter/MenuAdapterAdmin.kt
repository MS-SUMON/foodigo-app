package com.example.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datamodel.MenuItem
import com.example.user.R

class MenuAdapterAdmin(
    private val menuList: MutableList<MenuItem>,
    private val onDeleteClickListener: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapterAdmin.MenuViewHolder>() {

    // ViewHolder - view and metadata about its place within the RecyclerView.
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.menuFoodName_admin)
        val ownerName: TextView = itemView.findViewById(R.id.ownerNamePopular_admin)
        val price: TextView = itemView.findViewById(R.id.menuPrice_admin)
        val image: ImageView = itemView.findViewById(R.id.menuImage_admin)
        val deleteButton: ImageView = itemView.findViewById(R.id.btn_delete_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_for_admin, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuList[position]
        holder.foodName.text = menuItem.foodName
        holder.ownerName.text = menuItem.ownerName
        holder.price.text = menuItem.price

        menuItem.imageResId?.let {
            holder.image.setImageResource(it)
        }

        holder.deleteButton.setOnClickListener {

            onDeleteClickListener(menuItem)
        }
    }
}