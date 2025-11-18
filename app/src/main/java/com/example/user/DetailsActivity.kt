package com.example.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.user.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- 1. Retrieve ALL Data from the Intent ---
        val foodName = intent.getStringExtra("menuItemName")
        val foodPrice = intent.getStringExtra("menuItemPrice")
        val foodImage = intent.getIntExtra("menuItemImage", 0)
        val foodOwner = intent.getStringExtra("menuItemOwner")

        // --- 2. Bind Retrieved Data to Views ---
        binding.detailFoodName.text = foodName
        binding.detailFoodImage.setImageResource(foodImage)
        // Assuming your layout has TextViews for Price and Owner,
        // you would bind them here. For example:
        // binding.detailPrice.text = foodPrice
        // binding.detailOwnerName.text = foodOwner


        // --- 3. Set up Click Listeners ---

        // Back button: go to previous screen
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        // Add To Cart button: show a toast
        binding.addToCart.setOnClickListener {
            // Use the retrieved foodName in the toast message
            Toast.makeText(this, "$foodName added to cart", Toast.LENGTH_SHORT).show()
        }

        // Optional: handle system window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}