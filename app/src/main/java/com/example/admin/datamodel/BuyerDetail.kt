package com.example.admin.datamodel

data class BuyerDetail(
    val name: String,
    val address: String,
    val email: String,
    val phone: String,
    val totalOrders: Int,
    val totalExpenses: Double
)