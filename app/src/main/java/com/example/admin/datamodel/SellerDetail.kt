package com.example.admin.datamodel

data class SellerDetail(
    val restaurantName: String,
    val ownerName: String,
    val address: String,
    val email: String,
    val phone: String,
    val status: String,
    val commissionRate: String,
    val currentBalance: String,
    val pendingPayout: Double,
    val totalOrders: String,
    val completedOrders: String,
    val totalRevenue: String,
    val commissionPaid: String,
    val rating: String,
    val imageUrl: Int
)