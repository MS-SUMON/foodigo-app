package com.example.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datamodel.PendingPayoutItem
import com.example.user.R

class TransactionHistoryAdapter(private val transactions: List<PendingPayoutItem>) :
    RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSellerLogo: ImageView = itemView.findViewById(R.id.img_seller_logo)
        val tvSellerName: TextView = itemView.findViewById(R.id.trv_seller_name)
        val tvOwnerName: TextView = itemView.findViewById(R.id.tv_owner_name)
        val tvPayoutDate: TextView = itemView.findViewById(R.id.tv_payout_date)
        val tvPayoutAmount: TextView = itemView.findViewById(R.id.tv_payout_amount)
        val tvPayoutStatus: TextView = itemView.findViewById(R.id.tv_payout_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payout_transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.imgSellerLogo.setImageResource(transaction.imageResId)
        holder.tvSellerName.text = transaction.name
        holder.tvOwnerName.text = transaction.subtitle
        holder.tvPayoutDate.text = transaction.date
        holder.tvPayoutAmount.text = transaction.amount
        holder.tvPayoutStatus.text = "Completed"
    }

    override fun getItemCount(): Int = transactions.size
}
