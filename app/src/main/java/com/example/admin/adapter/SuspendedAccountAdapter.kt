package com.example.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datamodel.AccountItem
import com.example.user.R
import com.google.android.material.imageview.ShapeableImageView

class SuspendedAccountAdapter(
    private val accountList: List<AccountItem>,
    private val onResumeClicked: (AccountItem) -> Unit
) : RecyclerView.Adapter<SuspendedAccountAdapter.SuspendedAccountViewHolder>() {

    class SuspendedAccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ShapeableImageView = itemView.findViewById(R.id.img_account_logo)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_account_name)
        val ownerTextView: TextView = itemView.findViewById(R.id.tv_account_owner)
        val resumeButton: Button = itemView.findViewById(R.id.btn_resume)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuspendedAccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suspended_account, parent, false)
        return SuspendedAccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuspendedAccountViewHolder, position: Int) {
        val account = accountList[position]

        holder.imageView.setImageResource(account.imageResId)
        holder.nameTextView.text = account.name
        holder.ownerTextView.text = account.owner

        holder.resumeButton.setOnClickListener {
            onResumeClicked(account)
        }
    }

    override fun getItemCount() = accountList.size
}