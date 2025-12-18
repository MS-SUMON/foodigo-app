package com.example.admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.adapter.TransactionHistoryAdapter
import com.example.admin.datamodel.PendingPayoutItem
import com.example.user.R

class TransactionHistory : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionHistoryAdapter
    private val transactions = mutableListOf<PendingPayoutItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transaction_history)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.list_transaction_history_recycler)

        transactions.addAll(createSampleTransactions())
        adapter = TransactionHistoryAdapter(transactions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    private fun createSampleTransactions(): List<PendingPayoutItem> {
        return listOf(
            PendingPayoutItem(
                id = "T1",
                name = "Spacy fresh crab",
                subtitle = "by Sifat",
                date = "Dec 23, 2022",
                amount = "1200.00 TK",
                imageResId = R.drawable.menu1
            ),
            PendingPayoutItem(
                id = "T2",
                name = "Royal Burger",
                subtitle = "by John",
                date = "Dec 25, 2022",
                amount = "1800.00 TK",
                imageResId = R.drawable.menu2
            ),
            PendingPayoutItem(
                id = "T3",
                name = "Ami's Kitchen",
                subtitle = "by Ami",
                date = "Dec 26, 2022",
                amount = "3500.00 TK",
                imageResId = R.drawable.menu3
            )
        )
    }
}