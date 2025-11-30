package com.example.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.user.databinding.BuyAgainItemBinding // Assuming the layout name is buy_again_item.xml

class BuyAgainAdapter(
    private val buyAgainFoodName: ArrayList<String>,
    private val buyAgainFoodOwner: ArrayList<String>,
    private val buyAgainFoodPrice: ArrayList<String>,
    private val buyAgainFoodImage: ArrayList<Int>
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    fun removeItem(position: Int) {
        if (position >= 0 && position < buyAgainFoodName.size) {
            // Remove item from all corresponding lists
            buyAgainFoodName.removeAt(position)
            buyAgainFoodOwner.removeAt(position)
            buyAgainFoodPrice.removeAt(position)
            buyAgainFoodImage.removeAt(position)

            // Notify the adapter that the item has been removed
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(
            buyAgainFoodName[position],
            buyAgainFoodOwner[position],
            buyAgainFoodPrice[position],
            buyAgainFoodImage[position]
        )
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // 🗑️ SET DELETE LISTENER HERE
            binding.buyAgainTrash.setOnClickListener {
                // Get the position of the item being clicked
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position)
                    Toast.makeText(itemView.context, "Item Deleted", Toast.LENGTH_SHORT).show()
                }
            }

            // Buy Again Button Listener (from previous request)
            binding.buyAgainFoodButton.setOnClickListener {
                val context = itemView.context
                val name = binding.buyAgainFoodName.text.toString()
                Toast.makeText(context, "$name added to cart again", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(name: String, owner: String, price: String, imageRes: Int) {
            binding.buyAgainFoodName.text = name
            binding.buyAgainFoodOwner.text = owner
            binding.buyAgainFoodPrice.text = price
            binding.buyAgainFoodImage.setImageResource(imageRes)
        }
    }
}