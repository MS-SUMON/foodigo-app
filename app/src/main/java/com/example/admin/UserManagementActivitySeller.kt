package com.example.admin

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.adapter.UserAdapter
import com.example.admin.datamodel.User
import com.example.user.R
import com.google.android.material.tabs.TabLayout

class UserManagementActivitySeller : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var tabLayoutUsers: TabLayout
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_management_seller)

        btnBack = findViewById(R.id.btn_back)
        tabLayoutUsers = findViewById(R.id.tab_layout_users)
        recyclerViewUsers = findViewById(R.id.recycler_view_users)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        setupRecyclerView()
        setupTabLayout()
    }
    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }
    private fun setupRecyclerView() {
        val initialData = createSellerData()

        userAdapter = UserAdapter(this, initialData)

        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        recyclerViewUsers.adapter = userAdapter
    }

    private fun setupTabLayout() {
        tabLayoutUsers.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val newData = when (tab.position) {
                    0 -> createSellerData()
                    1 -> createBuyerData()
                    else -> emptyList()
                }
                userAdapter.updateList(newData)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        if (tabLayoutUsers.tabCount > 0) {
            tabLayoutUsers.getTabAt(0)?.select()
        }
    }
    private fun createSellerData(): List<User> {
        return listOf(
            User("S1", "Spicy fresh crab", "Lee", R.drawable.menu1), // Fixed: Removed .toString()
            User("S2", "The Burger Joint", "Mark", R.drawable.menu2), // Fixed: Removed .toString()
            User("S3", "Grand Biriyani House", "Khan", R.drawable.menu3), // Fixed: Removed .toString()
            User("S4", "Italian Pasta Co.", "Rossi", R.drawable.menu4) // Fixed: Removed .toString()
        )
    }
    private fun createBuyerData(): List<User> {
        return listOf(
            User("B1", "John Doe", "", R.drawable.profile), // Fixed: Removed .toString()
            User("B2", "Jane Smith", "", R.drawable.profile), // Fixed: Removed .toString()
            User("B3", "Michael Brown", "", R.drawable.profile), // Fixed: Removed .toString()
            User("B4", "Lisa White", "", R.drawable.profile) // Fixed: Removed .toString()
        )
    }
}