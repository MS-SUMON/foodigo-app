package com.example.foodigo.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodigo.databinding.FragmentHomeBinding
import com.example.foodigo.databinding.PopularItemBinding

class PopularAdapter(private val item: List<String>, private val price: List<String>, private val image: List<Int>) : RecyclerView.Adapter<PopularAdapter.PouplerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PouplerViewHolder {
        return PouplerViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: PouplerViewHolder, position: Int) {
        val item = item[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item, price ,images)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class PouplerViewHolder(private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val imagesView = binding.imageView4
        fun bind(item: String, price:String, images: Int){
            binding.foodNamePopular.text = item
            binding.pricePopular.text = price

            imagesView.setImageResource(images)

        }
    }
}



