package com.example.admin

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.adapters.SellerApplicationAdapter
import com.example.admin.datamodel.SellerApplication
import com.example.user.R

class SellerApplicationActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var recyclerViewApplications: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seller_application)

        btnBack = findViewById(R.id.btn_back)
        recyclerViewApplications = findViewById(R.id.recycler_view_applications)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        setupRecyclerView()
    }
    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }
    private fun setupRecyclerView() {
        val applicationsList = createSampleData().toMutableList()
        recyclerViewApplications.layoutManager = LinearLayoutManager(this)
        val adapter = SellerApplicationAdapter(applicationsList)
        recyclerViewApplications.adapter = adapter
    }
    private fun createSampleData(): List<SellerApplication> {
        return listOf(
            SellerApplication("1", R.drawable.menu2, "Spicy Fresh Crab", "by Sifat"),
            SellerApplication("2", R.drawable.menu2, "The Burger Joint", "by Ahad"),
            SellerApplication("3", R.drawable.menu4, "Grand Biriyani House", "by Rohan"),
            SellerApplication("4", R.drawable.menu1, "Italian Pasta Co.", "by Maria")
        )
    }
}
