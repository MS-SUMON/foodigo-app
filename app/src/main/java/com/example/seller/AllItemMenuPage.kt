package com.example.seller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.R
import android.widget.ImageButton
import com.example.seller.adapter.AllItemAdapter

class AllItemMenuPage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var backBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_item_menu_page)

        // For edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // View hooks
        backBtn = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.all_item_recycler_view)

        // Back button click
        backBtn.setOnClickListener {
            onBackPressed()
        }

        // Dummy Data (MutableList for deletion)
        val itemList = mutableListOf(
            "Burger",
            "Pizza",
            "Shawarma",
            "Pasta",
            "Fried Chicken",
            "Cold Coffee"
        )

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AllItemAdapter(itemList)

    }
}
