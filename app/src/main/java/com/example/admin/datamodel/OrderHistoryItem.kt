package com.example.admin.datamodel

data class OrderHistoryItem(
    val orderId: String,
    val restaurantName: String,
    val date: String,
    val amount: Double,
    val status: String,
    val itemSummary: String,
    val itemQuantity: Int,
    val itemImageUrl: String
)