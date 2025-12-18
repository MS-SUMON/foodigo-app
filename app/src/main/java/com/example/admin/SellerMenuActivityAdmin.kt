package com.example.admin

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.adapter.MenuAdapterAdmin
import com.example.admin.datamodel.MenuItem
import com.example.user.R

class SellerMenuActivityAdmin : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBack: ImageButton

    private lateinit var menuItems: MutableList<MenuItem>
    private lateinit var menuAdapter: MenuAdapterAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seller_menu_admin)

        // Edge-to-Edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recycler_view_menu)
        btnBack = findViewById(R.id.btn_back)
        val sellerNameTextView = findViewById<TextView>(R.id.tv_seller_name_header)


        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        menuItems = mutableListOf( // Use mutableListOf
            MenuItem("Spicy Hotpot", "Jane's Kitchen", "120 TK", R.drawable.hotpot_2),
            MenuItem("Chicken Biryani", "Raju Restaurant", "180 TK",R.drawable.menu1),
            MenuItem("Veggie Delight", "Healthy Meals", "85 TK",R.drawable.menu2),
            MenuItem("Taco Supreme", "Mexican Place", "95 TK",R.drawable.menu3)
        )

        menuAdapter = MenuAdapterAdmin(menuItems) { menuItemToDelete ->

            val position = menuItems.indexOf(menuItemToDelete)
            if (position != -1) {
                // Remove item from data source
                menuItems.removeAt(position)
                // Notify adapter of the removal at a specific position
                menuAdapter.notifyItemRemoved(position)
                // Notify adapter that the remaining items' positions have shifted
                menuAdapter.notifyItemRangeChanged(position, menuItems.size)
                Toast.makeText(
                    this,
                    "🗑️ Deleted ${menuItemToDelete.foodName}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        recyclerView.adapter = menuAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the seller name in the header
        val sellerName = intent.getStringExtra("SELLER_NAME") ?: "Admin Menu"
        sellerNameTextView.text = sellerName
    }
}