package com.example.admin.datamodel

data class MenuItem(
    val foodName: String,
    val ownerName: String,
    val price: String,
    val imageResId: Int? = null
)