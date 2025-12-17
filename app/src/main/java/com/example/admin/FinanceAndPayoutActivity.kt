package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.adapters.PendingPayoutAdapter
import com.example.admin.datamodel.PendingPayoutItem
import com.example.user.R

class FinanceAndPayoutActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvTransactionHistoryLink: TextView
    private lateinit var recyclerViewPendingPayout: RecyclerView
    private lateinit var btnRequestPayout: Button

    // Metric TextViews
    private lateinit var tvTotalRevenue: TextView
    private lateinit var tvTotalPayoutsMade: TextView
    private lateinit var tvCurrentPendingPayout: TextView
    private lateinit var tvPendingPayoutTotalValue: TextView

    private lateinit var userAdapter: PendingPayoutAdapter

    // Store data as mutable list
    private val pendingPayouts = mutableListOf<PendingPayoutItem>()

    // Metrics (must be mutable)
    private var totalRevenue: Double = 50000.00
    private var totalPayoutsMade: Double = 20000.00
    private var staticCurrentPendingPayout: Double = 50000.00 // must be var to update

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_finance_and_payout)

        // Initialize Views
        btnBack = findViewById(R.id.btn_back)
        tvTransactionHistoryLink = findViewById(R.id.tv_transaction_history)
        recyclerViewPendingPayout = findViewById(R.id.list_pending_payouts)
        btnRequestPayout = findViewById(R.id.btn_request_payout)

        // Metric TextViews
        tvTotalRevenue = findViewById(R.id.tv_total_revenue)
        tvTotalPayoutsMade = findViewById(R.id.tv_total_payouts_made)
        tvCurrentPendingPayout = findViewById(R.id.tv_current_pending_payout)
        tvPendingPayoutTotalValue = findViewById(R.id.pending_payout_total)

        // Apply insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load sample data
        pendingPayouts.addAll(createSampleData())
        setupRecyclerView(pendingPayouts)

        // Load metrics on start
        loadFinancialMetrics(pendingPayouts)

        setupListeners()
    }

    private fun loadFinancialMetrics(pendingList: List<PendingPayoutItem>) {

        val dynamicPendingTotal = pendingList.sumOf {
            it.amount.replace(" TK", "").toDoubleOrNull() ?: 0.0
        }

        tvTotalRevenue.text = String.format("%.2f TK", totalRevenue)
        tvTotalPayoutsMade.text = String.format("%.2f TK", totalPayoutsMade)
        tvCurrentPendingPayout.text = String.format("%.2f TK", staticCurrentPendingPayout)
        tvPendingPayoutTotalValue.text = String.format("%.2f TK", dynamicPendingTotal)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }

        // Only opens transaction history when clicked manually — not auto
        tvTransactionHistoryLink.setOnClickListener {
            val intent = Intent(this, TransactionHistory::class.java)
            startActivity(intent)
        }

        btnRequestPayout.setOnClickListener {
            performPayout()
        }
    }

    // UPDATED — NO PAGE NAVIGATION
    private fun performPayout() {
        if (pendingPayouts.isEmpty()) {
            Toast.makeText(this, "No pending payouts to request.", Toast.LENGTH_SHORT).show()
            return
        }

        val payoutAmount = pendingPayouts.sumOf {
            it.amount.replace(" TK", "").toDoubleOrNull() ?: 0.0
        }

        totalPayoutsMade += payoutAmount
        totalRevenue -= payoutAmount
        staticCurrentPendingPayout -= payoutAmount

        if (staticCurrentPendingPayout < 0) staticCurrentPendingPayout = 0.0
        if (totalRevenue < 0) totalRevenue = 0.0

        pendingPayouts.clear()
        userAdapter.notifyDataSetChanged()

        loadFinancialMetrics(pendingPayouts)

        Toast.makeText(
            this,
            String.format("Payout of %.2f TK successfully submitted!", payoutAmount),
            Toast.LENGTH_LONG
        ).show()

        // ❌ Removed — no automatic navigation
        // startTransactionHistoryActivity()
    }

    private fun setupRecyclerView(pendingList: List<PendingPayoutItem>) {
        userAdapter = PendingPayoutAdapter(pendingList)
        recyclerViewPendingPayout.layoutManager = LinearLayoutManager(this)
        recyclerViewPendingPayout.adapter = userAdapter
    }

    private fun createSampleData(): List<PendingPayoutItem> {
        return listOf(
            PendingPayoutItem(
                id = "P1",
                name = "Spacy fresh crab",
                subtitle = "by Sifat",
                date = "Dec 23, 2022",
                amount = "1200.00 TK",
                imageResId = R.drawable.menu1
            ),
            PendingPayoutItem(
                id = "P2",
                name = "Royal Burger",
                subtitle = "by John",
                date = "Dec 25, 2022",
                amount = "1800.00 TK",
                imageResId = R.drawable.menu2
            ),
            PendingPayoutItem(
                id = "P3",
                name = "Ami's Kitchen",
                subtitle = "by Ami",
                date = "Dec 26, 2022",
                amount = "3500.00 TK",
                imageResId = R.drawable.menu3
            )
        )
    }
}
