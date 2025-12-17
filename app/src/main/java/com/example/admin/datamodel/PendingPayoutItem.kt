package com.example.admin.datamodel

data class PendingPayoutItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val date: String,
    val amount: String,
    val imageResId: Int
)

