package com.example.admin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datamodel.PendingPayoutItem
import com.example.user.R // Contains references to drawables and layout

class PendingPayoutAdapter(
    private val payoutList: List<PendingPayoutItem>
) : RecyclerView.Adapter<PendingPayoutAdapter.PayoutViewHolder>() {

    class PayoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // IDs from payout_pending_item.xml
        val logo: ImageView = itemView.findViewById(R.id.img_seller_logo)
        val name: TextView = itemView.findViewById(R.id.tv_seller_name)
        val owner: TextView = itemView.findViewById(R.id.tv_owner_name)
        val date: TextView = itemView.findViewById(R.id.tv_payout_date)
        val amount: TextView = itemView.findViewById(R.id.tv_payout_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payout_pending_item, parent, false)
        return PayoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: PayoutViewHolder, position: Int) {
        val item = payoutList[position]

        holder.name.text = item.name
        holder.owner.text = item.subtitle
        holder.date.text = item.date
        holder.amount.text = item.amount

        // --- Image loading from Drawable ---
        if (item.imageResId != null && item.imageResId != 0) {
            holder.logo.setImageResource(item.imageResId)
        } else {
            // Set a default image if none is provided
            holder.logo.setImageResource(R.drawable.menu4)
        }
        // --- End Image loading ---
    }

    override fun getItemCount(): Int {
        return payoutList.size
    }
}