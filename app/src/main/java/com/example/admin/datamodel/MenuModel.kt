package com.example.admin.datamodel

data class MenuModel(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: Int // Resource ID for the menu item image
)