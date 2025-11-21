package com.example.seller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.LoginActivity
import com.example.user.databinding.ActivityMainSellerBinding


class MainActivitySeller : AppCompatActivity() {

    // Initialize View Binding instance
    private val binding: ActivityMainSellerBinding by lazy {
        ActivityMainSellerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Set up click listeners for all interactive elements
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // 1. Add Menu CardView
        binding.addMenuCardView.setOnClickListener {
            // Navigate to the Add Menu Activity
            navigateToActivity(AddMenuPage::class.java, "Add Menu")
        }

        // 2. All Item Menu CardView
        binding.allAddMenuCardView.setOnClickListener {
            // Navigate to the All Item Menu Activity
            navigateToActivity(AllItemMenuPage::class.java, "All Item Menu")
        }

        // 3. Order Dispatch CardView
        binding.orderDispatchCardView.setOnClickListener {
            // Navigate to the Order Dispatch Activity
            navigateToActivity(OrderDispatchPage::class.java, "Order Dispatch")
        }

        binding.profileCardView.setOnClickListener {
            // Navigate to the Profile Activity
            navigateToActivity(ProfilePage::class.java, "Profile")
        }

        // 5. Log Out Button
        binding.button7.setOnClickListener {
            performLogout()
        }
    }


    private fun navigateToActivity(targetActivity: Class<*>, activityName: String) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }

    /**
     * Handles the logout process: signs out the user and redirects to the login screen.
     */
    private fun performLogout() {
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()

        // --- Actual Logout Logic (Placeholder) ---
        // In a real application, you would perform actions here like:
        // 1. Calling Firebase/Auth.signOut()
        // 2. Clearing local session data (SharedPreferences)
        // -----------------------------------------

        // Navigate to the Login Page (assuming AdminLoginPage is your main login)
        val intent = Intent(this, LoginActivity::class.java)

        // These flags ensure the user cannot navigate back to the dashboard using the back button
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        finish() // Finish the current dashboard activity
    }
}