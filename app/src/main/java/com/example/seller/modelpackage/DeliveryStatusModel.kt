package com.example.seller.modelpackage

data class DeliveryStatusModel(
    val customerName: String,
    val paymentStatus: String,
    val deliveryStatus: String,
    val isDelivered: Boolean
)