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
    private lateinit var tvTotalRevenue: TextView
    private lateinit var tvTotalPayoutsMade: TextView
    private lateinit var tvCurrentPendingPayout: TextView
    private lateinit var tvPendingPayoutTotalValue: TextView
    private lateinit var userAdapter: PendingPayoutAdapter

    private val pendingPayouts = mutableListOf<PendingPayoutItem>()
    private var totalRevenue: Double = 50000.00
    private var totalPayoutsMade: Double = 20000.00
    private var staticCurrentPendingPayout: Double = 6500.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_finance_and_payout)

        // Initialize Views
        btnBack = findViewById(R.id.btn_back)
        tvTransactionHistoryLink = findViewById(R.id.tv_transaction_history)
        recyclerViewPendingPayout = findViewById(R.id.list_pending_payouts)
        btnRequestPayout = findViewById(R.id.btn_request_payout)

        tvTotalRevenue = findViewById(R.id.tv_total_revenue)
        tvTotalPayoutsMade = findViewById(R.id.tv_total_payouts_made)
        tvCurrentPendingPayout = findViewById(R.id.tv_current_pending_payout)
        tvPendingPayoutTotalValue = findViewById(R.id.pending_payout_total)

        // Apply edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pendingPayouts.clear()
        pendingPayouts.addAll(createSampleData())

        setupRecyclerView(pendingPayouts)
        loadFinancialMetrics(pendingPayouts)
        setupListeners()
    }
    private fun loadFinancialMetrics(pendingList: List<PendingPayoutItem>) {
        val dynamicPendingTotal = pendingList.sumOf {
            it.amount.replace(" TK", "").replace(",", "").toDoubleOrNull() ?: 0.0
        }

        tvTotalRevenue.text = String.format("%.2f TK", totalRevenue)
        tvTotalPayoutsMade.text = String.format("%.2f TK", totalPayoutsMade)
        tvCurrentPendingPayout.text = String.format("%.2f TK", staticCurrentPendingPayout)
        tvPendingPayoutTotalValue.text = String.format("%.2f TK", dynamicPendingTotal)
    }
    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }

        tvTransactionHistoryLink.setOnClickListener {
            val intent = Intent(this, TransactionHistory::class.java)
            startActivity(intent)
        }
        btnRequestPayout.setOnClickListener {
            performPayout()
        }
    }
    private fun performPayout() {
        if (pendingPayouts.isEmpty()) {
            Toast.makeText(this, "No pending payouts to request.", Toast.LENGTH_SHORT).show()
            return
        }
        val payoutAmount = pendingPayouts.sumOf {
            it.amount.replace(" TK", "").replace(",", "").toDoubleOrNull() ?: 0.0
        }

        totalPayoutsMade += payoutAmount

        staticCurrentPendingPayout = 0.0
        pendingPayouts.clear()
        userAdapter.notifyDataSetChanged()

        loadFinancialMetrics(pendingPayouts)
        Toast.makeText(
            this,
            String.format("Payout of %.2f TK successfully submitted!", payoutAmount),
            Toast.LENGTH_LONG
        ).show()
    }
    private fun setupRecyclerView(pendingList: List<PendingPayoutItem>) {
        userAdapter = PendingPayoutAdapter(pendingList)
        recyclerViewPendingPayout.layoutManager = LinearLayoutManager(this)
        recyclerViewPendingPayout.adapter = userAdapter
    }
    private fun createSampleData(): List<PendingPayoutItem> {
        return listOf(
            PendingPayoutItem("P1", "Spacy fresh crab", "by Sifat", "Dec 23, 2022", "1200.00 TK", R.drawable.menu1),
            PendingPayoutItem("P2", "Royal Burger", "by John", "Dec 25, 2022", "1800.00 TK", R.drawable.menu2),
            PendingPayoutItem("P3", "Ami's Kitchen", "by Ami", "Dec 26, 2022", "3500.00 TK", R.drawable.menu3)
        )
    }
}