package com.example.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ViewSellerProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_seller_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sellerEmail = intent.getStringExtra("sellerEmail") ?: ""

        // Set seller email
        val sellerEmailTv = findViewById<TextView>(R.id.sellerEmail)
        sellerEmailTv.text = sellerEmail

        // Back button
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
        // View Menu button
        val viewMenuBtn = findViewById<Button>(R.id.viewSellerMenuBtn)
        viewMenuBtn.setOnClickListener {
            val intent = Intent(this, SellerMenuActivity::class.java)
            intent.putExtra("sellerEmail", sellerEmail)
            startActivity(intent)
        }
    }
}
