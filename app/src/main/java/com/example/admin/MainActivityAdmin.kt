package com.example.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.user.R // Assumed R file location

class MainActivityAdmin : AppCompatActivity() {

    // Declare CardViews corresponding to the dashboard items
    private lateinit var cardSellerApplications: CardView
    private lateinit var cardUserManagement: CardView
    private lateinit var cardFinance: CardView
    private lateinit var cardSuspend: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Assuming your layout file is named activity_main_admin.xml
        setContentView(R.layout.activity_main_admin)

        // 1. Initialize views by ID from the XML layout
        initializeViews()

        // 2. Apply window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Setup navigation listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        // Ensure these IDs match the CardView IDs in activity_main_admin.xml
        cardSellerApplications = findViewById(R.id.card_seller_applications)
        cardUserManagement = findViewById(R.id.card_user_management)
        cardFinance = findViewById(R.id.card_finance)
        cardSuspend = findViewById(R.id.card_suspend)
    }

    private fun setupClickListeners() {
        // --- 1. Seller Applications ---
        cardSellerApplications.setOnClickListener {
            // Navigate to the screen for reviewing new seller applications
            val intent = Intent(this, SellerApplicationActivity::class.java)
            startActivity(intent)
        }

        // --- 2. User Management ---
        cardUserManagement.setOnClickListener {
            // Navigate to the screen for managing user/seller accounts
            // NOTE: Renamed to UserManagementActivityAdmin for clarity, check your actual class name.
            val intent = Intent(this, UserManagementActivitySeller::class.java)
            startActivity(intent)
        }

        // --- 3. Finance and Payout ---
        cardFinance.setOnClickListener {
            // Navigate to the finance dashboard
            val intent = Intent(this, FinanceAndPayoutActivity::class.java)
            startActivity(intent)
        }

        // --- 4. Suspend/Block Accounts ---
        cardSuspend.setOnClickListener {
            // Navigate to the screen for suspending/blocking accounts
            val intent = Intent(this, SuspendAccountsActivity::class.java)
            startActivity(intent)
        }
    }
}