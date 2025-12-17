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

        // --- ViewBinding setup ---
        binding = ActivitySellerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Back Button is functional
        // If you updated the XML ID to 'backButton', this line is correct:
        binding.backButton.setOnClickListener {
            finish()
        }

        // --- RecyclerView setup ---
        menuList = ArrayList()
        adapter = SellerMenuAdapter(this, menuList)
        binding.sellerMenuRecycler.layoutManager = LinearLayoutManager(this)
        binding.sellerMenuRecycler.adapter = adapter

        // --- Load new dummy menu items ---
        loadDummyMenu()
    }

    private fun loadDummyMenu() {
        // New Data provided by the user
        val foodName = listOf("Rice")
        val ownerName = listOf("Happy Tastes")
        val price = listOf("150 TK")

        // Reusing existing drawables. Adjust if you have new drawable names.
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