package com.example.user

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
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

        // --- Retrieve Data from Intent ---
        val foodName = intent.getStringExtra("menuItemName")
        val foodPrice = intent.getStringExtra("menuItemPrice") // already in TK
        val foodImage = intent.getIntExtra("menuItemImage", 0)
        val sellerEmail = intent.getStringExtra("sellerEmail")

        // --- Bind Data to Views ---
        binding.detailFoodName.text = foodName
        binding.detailFoodPrice.text = foodPrice
        binding.detailFoodImage.setImageResource(foodImage)

        // --- Back Button ---
        binding.backButton.setOnClickListener { onBackPressed() }

        // --- Add To Cart ---
        binding.addToCart.setOnClickListener {
            Toast.makeText(this, "$foodName added to cart", Toast.LENGTH_SHORT).show()
        }

        // --- View Seller Profile ---
        binding.viewSellerProfile.setOnClickListener {
            val intent = Intent(this, ViewSellerProfile::class.java)
            intent.putExtra("sellerEmail", sellerEmail)
            startActivity(intent)
        }

        // --- Submit Review ---
        binding.submitReviewBtn.setOnClickListener {
            val reviewText = binding.reviewInput.text.toString().trim()
            if (reviewText.isEmpty()) {
                Toast.makeText(this, "Please write a review", Toast.LENGTH_SHORT).show()
            } else {
                addReviewToList(reviewText)
                binding.reviewInput.text.clear()
                Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show()
            }
        }

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addReviewToList(reviewText: String) {
        val reviewContainer = binding.reviewListContainer
        val newReview = TextView(this)
        newReview.text = "• $reviewText"
        newReview.textSize = 16f
        newReview.setPadding(8, 8, 8, 8)
        newReview.setTextColor(resources.getColor(android.R.color.black, null))
        reviewContainer.addView(newReview)
    }
}
