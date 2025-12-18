package com.example.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.BuyerHistoryActivity
import com.example.admin.SellerProfileActivityForAdmin
import com.example.admin.datamodel.User
import com.example.user.R
import com.google.android.material.imageview.ShapeableImageView

class UserAdapter(
    private val context: Context,
    private var userList: List<User>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SELLER = 1
        private const val VIEW_TYPE_BUYER = 2

        private val LAYOUT_SELLER_ITEM = R.layout.user_management_recycler_view_seller
        private val LAYOUT_BUYER_ITEM = R.layout.user_management_recycler_view_buyer
    }
    //  ViewHolders
    class SellerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_user_name_seller)
        val subtitleTextView: TextView = view.findViewById(R.id.tv_user_subtitle_seller)
        val profileImageView: ShapeableImageView = view.findViewById(R.id.iv_profile_image_seller)
        val btnViewProfile: Button = view.findViewById(R.id.btn_view_profile)
    }
    class BuyerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_user_name_seller)
        val profileImageView: ShapeableImageView = view.findViewById(R.id.iv_profile_image_seller)
        val btnViewHistory: Button = view.findViewById(R.id.btn_view_profile)
    }

    //  Adapter Overrides

    override fun getItemViewType(position: Int): Int {
        return if (userList[position].subtitle.isNotEmpty()) VIEW_TYPE_SELLER else VIEW_TYPE_BUYER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SELLER -> {
                val view = LayoutInflater.from(context).inflate(LAYOUT_SELLER_ITEM, parent, false)
                SellerViewHolder(view)
            }
            VIEW_TYPE_BUYER -> {
                val view = LayoutInflater.from(context).inflate(LAYOUT_BUYER_ITEM, parent, false)
                BuyerViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = userList[position]

        when (holder) {
            is SellerViewHolder -> {
                // Set data
                holder.nameTextView.text = user.name
                holder.subtitleTextView.text = user.subtitle
                holder.profileImageView.setImageResource(user.image)

                // Intent to SellerProfileActivityForAdmin
                holder.btnViewProfile.setOnClickListener {
                    val intent = Intent(context, SellerProfileActivityForAdmin::class.java).apply {
                        putExtra("USER_ID", user.id)
                        putExtra("SELLER_NAME", user.name)
                        putExtra("OWNER_NAME", user.subtitle)
                    }
                    context.startActivity(intent)
                }
            }
            is BuyerViewHolder -> {
                holder.nameTextView.text = user.name
                holder.profileImageView.setImageResource(user.image)

                // Intent to BuyerHistoryActivity
                holder.btnViewHistory.setOnClickListener {
                    val intent = Intent(context, BuyerHistoryActivity::class.java).apply {
                        putExtra("USER_ID", user.id)
                        putExtra("BUYER_NAME", user.name)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = userList.size

    fun updateList(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }
}