package com.example.foodigo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodigo.databinding.CartItemBinding

class CartAdapter(
    private val CardItems: MutableList<String>,
    private val CartItemPrices: MutableList<String>,
    private val CartImage: MutableList<Int>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>()
{

    private val itemQuantities = IntArray(CardItems.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CardItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = CardItems[position]
                cartItemPrice.text = CartItemPrices[position]
                cartImage.setImageResource(CartImage[position])
                cartItemQuantity.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                    }



                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }


                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION)
                        deleteItem(itemPosition)

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
            CardItems.removeAt(position)
            CartImage.removeAt(position)
            CartItemPrices.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position , CardItems.size)
        }
    }
}
