package com.example.admin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datamodel.SellerApplication
import com.example.user.R
import com.google.android.material.imageview.ShapeableImageView

class SellerApplicationAdapter(
    private val applicationsList: MutableList<SellerApplication>
) : RecyclerView.Adapter<SellerApplicationAdapter.ApplicationViewHolder>() {

    class ApplicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ShapeableImageView = itemView.findViewById(R.id.iv_profile_image_seller)
        val sellerName: TextView = itemView.findViewById(R.id.tv_seller_name)
        val sellerSubtitle: TextView = itemView.findViewById(R.id.tv_seller_subtitle)
        val btnApprove: Button = itemView.findViewById(R.id.btn_approve)
        val btnReject: Button = itemView.findViewById(R.id.btn_reject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.seller_application_recycler_view, parent, false)
        return ApplicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val application = applicationsList[position]

        holder.sellerName.text = application.name
        holder.sellerSubtitle.text = application.subtitle
        holder.profileImage.setImageResource(application.imageResId)

        holder.btnApprove.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Approved ${application.name}", Toast.LENGTH_SHORT).show()
            removeItem(holder.adapterPosition) // using adapterPosition instead of position
        }

        holder.btnReject.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Rejected ${application.name}", Toast.LENGTH_SHORT).show()
            removeItem(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = applicationsList.size

    private fun removeItem(position: Int) {
        if (position in 0 until applicationsList.size) {
            applicationsList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, applicationsList.size) // fix RecyclerView update
        }
    }
}
