package com.example.admin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datamodel.OrderHistoryItem
import com.example.user.R

class OrderHistoryAdapter(
    private val orderList: List<OrderHistoryItem>
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.tv_order_date)
        val statusTextView: TextView = itemView.findViewById(R.id.tv_order_status)
        val itemImageView: ImageView = itemView.findViewById(R.id.img_item_image)
        val summaryTextView: TextView = itemView.findViewById(R.id.tv_items_summary)
        val quantityTextView: TextView = itemView.findViewById(R.id.tv_item_quantity)
        val totalAmountTextView: TextView = itemView.findViewById(R.id.tv_total_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_history_item_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        holder.dateTextView.text = order.date
        holder.summaryTextView.text = order.itemSummary
        holder.quantityTextView.text = "x${order.itemQuantity}"
        holder.totalAmountTextView.text = String.format("%.2f TK", order.amount)

        setStatusStyle(holder.statusTextView, order.status)
        loadImage(holder.itemImageView, order.itemImageUrl)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    private fun setStatusStyle(textView: TextView, status: String) {
        val (bgColor, textColor) = when (status.uppercase()) {
            "DELIVERED" -> Pair("#4CAF50", "#FFFFFF")
            "PENDING" -> Pair("#FFC107", "#000000")
            "CANCELED" -> Pair("#F44336", "#FFFFFF")
            "SHIPPED" -> Pair("#2196F3", "#FFFFFF")
            else -> Pair("#BDBDBD", "#000000")
        }

        textView.setBackgroundColor(Color.parseColor(bgColor))
        textView.setTextColor(Color.parseColor(textColor))
    }

    private fun loadImage(imageView: ImageView, imageUrl: String) {
        val resourceId = imageUrl.toIntOrNull()
        if (resourceId != null && resourceId != 0) {
            imageView.setImageResource(resourceId)
        } else {
            imageView.setImageResource(R.drawable.menu1)
        }
    }
}