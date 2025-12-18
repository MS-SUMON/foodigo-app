package com.example.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user.adapter.SellerMenuAdapter
import com.example.user.databinding.ActivitySellerMenuBinding
import com.example.user.modelpackageforuser.MenuModel
class SellerMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerMenuBinding
    private lateinit var menuList: ArrayList<MenuModel>
    private lateinit var adapter: SellerMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySellerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        menuList = ArrayList()
        adapter = SellerMenuAdapter(this, menuList)
        binding.sellerMenuRecycler.layoutManager = LinearLayoutManager(this)
        binding.sellerMenuRecycler.adapter = adapter
        loadDummyMenu()
    }

    private fun loadDummyMenu() {
        val foodName = listOf("Rice")
        val ownerName = listOf("Happy Tastes")
        val price = listOf("150 TK")
        val imageDrawables = listOf("menu1", "menu2", "menu3", "menu4", "menu5", "menu1")

        // Clear old data and populate with new data
        menuList.clear()
        // Loop through the lists and add MenuModel objects
        for (i in foodName.indices) {
            menuList.add(MenuModel(foodName[i], ownerName[i], price[i], imageDrawables[i]))
        }
        adapter.notifyDataSetChanged()
    }
}