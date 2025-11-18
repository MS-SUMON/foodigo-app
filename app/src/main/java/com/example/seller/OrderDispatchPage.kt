package com.example.seller

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodigo_seller.adapter.DeliveryStatusAdapter
import com.example.seller.modelpackage.DeliveryStatusModel
import com.example.user.R


class OrderDispatchPage : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeliveryStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_dispatch_page)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup
        backButton = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.delivery_recycler_view)

        backButton.setOnClickListener {
            onBackPressed()
        }


        val orderList = arrayListOf(
            DeliveryStatusModel("Sabbir Ahmed", "Received", "Delivered", true),
            DeliveryStatusModel("Nusrat Jahan", "Not Received", "Pending", false),
            DeliveryStatusModel("Rakib Khan", "Received", "Delivered", true),
            DeliveryStatusModel("Sadman Sakib", "Not Received", "Out for Delivery", false)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DeliveryStatusAdapter(orderList)
        recyclerView.adapter = adapter
    }
}