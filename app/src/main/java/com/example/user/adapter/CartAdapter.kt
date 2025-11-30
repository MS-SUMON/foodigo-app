package com.example.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.user.databinding.CartItemBinding

class CartAdapter(
    private val cartFoodNames: MutableList<String>,
    private val cartOwnerNames: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private val cartImages: MutableList<Int>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val itemQuantities = IntArray(cartFoodNames.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartFoodNames.size

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val quantity = itemQuantities[position]

            binding.apply {
                cartFoodName.text = cartFoodNames[position]
                cartOwnerName.text = cartOwnerNames[position] // NEW
                cartItemPrice.text = cartItemPrices[position]
                cartImage.setImageResource(cartImages[position])
                cartItemQuantity.text = quantity.toString()

                // Increase quantity
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }

                // Decrease quantity
                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }

                // Delete item
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
            cartFoodNames.removeAt(position)
            cartOwnerNames.removeAt(position)
            cartItemPrices.removeAt(position)
            cartImages.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartFoodNames.size)
        }
    }
}
