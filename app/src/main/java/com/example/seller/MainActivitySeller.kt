package com.example.seller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.LoginActivity
import com.example.user.databinding.ActivityMainSellerBinding


class MainActivitySeller : AppCompatActivity() {
    private val binding: ActivityMainSellerBinding by lazy {
        ActivityMainSellerBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        //Add Menu CardView
        binding.addMenuCardView.setOnClickListener {
            // Navigate to the Add Menu Activity
            navigateToActivity(AddMenuPage::class.java, "Add Menu")
        }

        //All Item Menu CardView
        binding.allAddMenuCardView.setOnClickListener {
            // Navigate to the All Item Menu Activity
            navigateToActivity(AllItemMenuPage::class.java, "All Item Menu")
        }

        // Order Dispatch CardView
        binding.orderDispatchCardView.setOnClickListener {
            // Navigate to the Order Dispatch Activity
            navigateToActivity(OrderDispatchPage::class.java, "Order Dispatch")
        }

        binding.profileCardView.setOnClickListener {
            //Profile Activity
            navigateToActivity(ProfilePage::class.java, "Profile")
        }

        // Log Out Button
        binding.button7.setOnClickListener {
            performLogout()
        }
    }
    private fun navigateToActivity(targetActivity: Class<*>, activityName: String) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }
    private fun performLogout() {
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}