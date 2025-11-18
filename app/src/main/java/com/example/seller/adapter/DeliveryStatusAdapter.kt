package com.example.foodigo_seller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seller.modelpackage.DeliveryStatusModel
import com.example.user.R


class DeliveryStatusAdapter(private val dataList: ArrayList<DeliveryStatusModel>) :
    RecyclerView.Adapter<DeliveryStatusAdapter.DeliveryViewHolder>() {

    class DeliveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.label_customer_name)
        val paymentStatus: TextView = itemView.findViewById(R.id.payment_status_text)
        val deliveryStatus: TextView = itemView.findViewById(R.id.delivery_status_text)
        val indicator: View = itemView.findViewById(R.id.status_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delivary_status, parent, false)
        return DeliveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val item = dataList[position]

        holder.name.text = item.customerName
        holder.paymentStatus.text = item.paymentStatus
        holder.deliveryStatus.text = item.deliveryStatus

        // Green or Red dot
        if (item.isDelivered) {
            holder.indicator.setBackgroundResource(R.drawable.green_dot)
        } else {
            holder.indicator.setBackgroundResource(R.drawable.red_dot)
        }
    }

    override fun getItemCount(): Int = dataList.size
}
