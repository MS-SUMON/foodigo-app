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

        // --- Back Button ---
        binding.backButton.setOnClickListener {
            finish() // This will work
        }

        // --- RecyclerView setup ---
        menuList = ArrayList()
        adapter = SellerMenuAdapter(this, menuList)
        binding.sellerMenuRecycler.layoutManager = LinearLayoutManager(this)
        binding.sellerMenuRecycler.adapter = adapter

        // --- Load dummy menu items with drawable names ---
        loadDummyMenu()
    }

    private fun loadDummyMenu() {
        menuList.add(MenuModel("Strawberry Cake", "Ornali", "250 Tk", "menu1"))
        menuList.add(MenuModel("Chocolate Hotpot", "Ornali", "350 Tk", "menu2"))
        menuList.add(MenuModel("Vanilla Ice Cream", "Ornali", "150 Tk", "menu3"))
        menuList.add(MenuModel("Fried Rice", "Ornali", "180 Tk", "menu4"))
        menuList.add(MenuModel("Mango Smoothie", "Ornali", "120 Tk", "menu5"))

        adapter.notifyDataSetChanged()
    }
}
