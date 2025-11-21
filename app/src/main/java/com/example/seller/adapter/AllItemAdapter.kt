package com.example.seller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.user.R


class AllItemAdapter(private val itemList: MutableList<String>) :
    RecyclerView.Adapter<AllItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val quantityText: TextView = itemView.findViewById(R.id.quantity_text)
        val plusButton: ImageView = itemView.findViewById(R.id.plus_button)
        val minusButton: ImageButton = itemView.findViewById(R.id.minusButton)
        val trashButton: ImageView = itemView.findViewById(R.id.item_image)
    }

    // Initial quantity for each item is 1
    private val quantityList = MutableList(itemList.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all_menu, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemName.text = itemList[position]
        holder.quantityText.text = quantityList[position].toString()

        // Increase quantity
        holder.plusButton.setOnClickListener {
            quantityList[position]++
            holder.quantityText.text = quantityList[position].toString()
        }

        // Decrease quantity
        holder.minusButton.setOnClickListener {
            if (quantityList[position] > 1) { // prevent going below 1
                quantityList[position]--
                holder.quantityText.text = quantityList[position].toString()
            }
        }

        // Delete item
        holder.trashButton.setOnClickListener {
            itemList.removeAt(position)
            quantityList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemList.size)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
