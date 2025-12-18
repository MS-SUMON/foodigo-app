package com.example.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.user.R
import com.example.user.LoginActivity // আপনার প্রজেক্টের LoginActivity ইমপোর্ট করুন
import com.google.firebase.auth.FirebaseAuth

class MainActivityAdmin : AppCompatActivity() {

    private lateinit var cardSellerApplications: CardView
    private lateinit var cardUserManagement: CardView
    private lateinit var cardFinance: CardView
    private lateinit var cardSuspend: CardView
    private lateinit var btnLogout: AppCompatButton // বাটন ডিক্লারেশন

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_admin)

        initializeViews()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun initializeViews() {
        cardSellerApplications = findViewById(R.id.card_seller_applications)
        cardUserManagement = findViewById(R.id.card_user_management)
        cardFinance = findViewById(R.id.card_finance)
        cardSuspend = findViewById(R.id.card_suspend)
        btnLogout = findViewById(R.id.logoutfromadmin) // আইডি অনুযায়ী বাটন ইনিশিয়ালাইজ
    }

    private fun setupClickListeners() {
        cardSellerApplications.setOnClickListener {
            startActivity(Intent(this, SellerApplicationActivity::class.java))
        }

        cardUserManagement.setOnClickListener {
            startActivity(Intent(this, UserManagementActivitySeller::class.java))
        }

        cardFinance.setOnClickListener {
            startActivity(Intent(this, FinanceAndPayoutActivity::class.java))
        }

        cardSuspend.setOnClickListener {
            startActivity(Intent(this, SuspendAccountsActivity::class.java))
        }

        // --- Logout Button Logic ---
        btnLogout.setOnClickListener {
            // ১. Firebase থেকে সাইন আউট করা
            FirebaseAuth.getInstance().signOut()

            // ২. লগইন স্ক্রিনে পাঠিয়ে দেওয়া এবং ব্যাক স্ট্যাক ক্লিয়ার করা
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // ৩. বর্তমান অ্যাক্টিভিটি শেষ করা
            finish()
        }
    }
}