package com.example.user.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.user.DetailsActivity
import com.example.user.databinding.PopularItemBinding

class PopularAdapter(
    private val foodName: List<String>,
    private val price: List<String>,
    private val imageList: List<Int>,
    private val ownerList: List<String>
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun getItemCount(): Int = foodName.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = PopularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        // Use the lists to bind data
        holder.bind(
            foodName[position],
            price[position],
            imageList[position],
            ownerList[position]
        )

        // ✅ FIX 1: Uncomment and implement the click listener
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            val intent = Intent(context, DetailsActivity::class.java).apply {
                // ✅ FIX 2: Use the exact keys expected by DetailsActivity.kt
                putExtra("menuItemName", foodName[position])
                putExtra("menuItemImage", imageList[position])

                // Add other useful data, even if not used yet in DetailsActivity
                putExtra("menuItemPrice", price[position])
                putExtra("menuItemOwner", ownerList[position])
            }
            context.startActivity(intent)
        }
    }

    inner class PopularViewHolder(private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: String, itemPrice: String, imageRes: Int, ownerName: String) {
            binding.foodNamePopular.text = itemName
            binding.pricePopular.text = itemPrice
            binding.ownerNamePopular.text = ownerName
            binding.foodImage.setImageResource(imageRes)
        }
    }
}