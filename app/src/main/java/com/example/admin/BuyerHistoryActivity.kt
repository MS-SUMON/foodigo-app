package com.example.admin

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
import com.example.user.R
import com.example.admin.adapter.OrderHistoryAdapter
import com.example.admin.datamodel.BuyerDetail
import com.example.admin.datamodel.OrderHistoryItem

class BuyerHistoryActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvBuyerNameHeader: TextView
    private lateinit var tvBuyerName: TextView
    private lateinit var tvBuyerAddress: TextView
    private lateinit var tvBuyerEmail: TextView
    private lateinit var tvBuyerPhone: TextView
    private lateinit var tvTotalOrders: TextView
    private lateinit var tvTotalExpenses: TextView
    private lateinit var recyclerOrderHistory: RecyclerView
    private lateinit var tvViewMoreHistory: TextView
    private lateinit var btnBlock: Button
    private lateinit var tvTitleBuyerHistory: TextView

    private var buyerId: String? = null
    private var buyerNameFromAdapter: String? = null // New variable to hold passed name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buyer_history)

        buyerId = intent.getStringExtra("USER_ID") ?: "Unknown Buyer"
        // ✅ FIX 2: Retrieve the Buyer Name passed from the Adapter
        buyerNameFromAdapter = intent.getStringExtra("BUYER_NAME")

        initializeViews()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadBuyerData(buyerId)
        setupListeners()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btn_back)
        tvBuyerNameHeader = findViewById(R.id.tv_buyer_name_header)
        tvBuyerName = findViewById(R.id.tv_buyer_name)
        tvBuyerAddress = findViewById(R.id.tv_buyer_address)
        tvBuyerEmail = findViewById(R.id.tv_buyer_email)
        tvBuyerPhone = findViewById(R.id.tv_buyer_phone)
        tvTotalOrders = findViewById(R.id.tv_total_orders)
        tvTotalExpenses = findViewById(R.id.tv_total_expenses)
        recyclerOrderHistory = findViewById(R.id.recycler_order_history)
        tvViewMoreHistory = findViewById(R.id.tv_view_more_history)
        btnBlock = findViewById(R.id.btn_block)
        tvTitleBuyerHistory = findViewById(R.id.tv_title_buyer_history)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnBlock.setOnClickListener {
            Toast.makeText(this, "Blocking user $buyerId", Toast.LENGTH_SHORT).show()
        }

        tvViewMoreHistory.setOnClickListener {
            Toast.makeText(this, "Viewing full history for $buyerId", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadBuyerData(id: String?) {
        val buyerDetails = getDummyBuyerDetail(id)
        val orderHistory = getDummyOrderHistory()

        // 🎯 FIX 3: Use the name passed from the adapter for the header and profile fields,
        // falling back to dummy data if the Intent name is null.
        val finalBuyerName = buyerNameFromAdapter ?: buyerDetails.name

        tvBuyerNameHeader.text = finalBuyerName
        tvBuyerName.text = finalBuyerName

        // Use the rest of the details from the dummy data
        tvBuyerAddress.text = buyerDetails.address
        tvBuyerEmail.text = buyerDetails.email
        tvBuyerPhone.text = buyerDetails.phone

        tvTotalOrders.text = buyerDetails.totalOrders.toString()
        tvTotalExpenses.text = String.format("%.2f TK", buyerDetails.totalExpenses)

        val orderAdapter = OrderHistoryAdapter(orderHistory)
        recyclerOrderHistory.layoutManager = LinearLayoutManager(this)
        recyclerOrderHistory.adapter = orderAdapter

        // Update the history title
        tvTitleBuyerHistory.text = "${finalBuyerName} History"
    }

    // NOTE: Assuming BuyerDetail and OrderHistoryItem are available data classes
    private fun getDummyBuyerDetail(id: String?): BuyerDetail {
        return when (id) {
            "B1" -> BuyerDetail("John Doe", "123 Main St, Block A", "john@mail.com", "9012345678", 45, 12500.50)
            "B2" -> BuyerDetail("Jane Smith", "45 Oak Ave, Apt 2B", "jane@mail.com", "9123456789", 12, 3450.00)
            else -> BuyerDetail("Unknown Buyer", "N/A", "N/A", "N/A", 0, 0.00)
        }
    }

    private fun getDummyOrderHistory(): List<OrderHistoryItem> {
        // NOTE: OrderHistoryItem's itemImageUrl should ideally also be an Int resource ID,
        // but it is left as String here to match your provided code snippet.
        return listOf(
            OrderHistoryItem(
                orderId = "O101",
                restaurantName = "The Burger Joint",
                date = "2024-11-20",
                amount = 550.00,
                status = "Delivered",
                itemSummary = "Spicy Chicken Burger",
                itemQuantity = 2,
                itemImageUrl = R.drawable.menu1.toString()
            ),
            OrderHistoryItem(
                orderId = "O102",
                restaurantName = "Spicy fresh crab",
                date = "2024-11-18",
                amount = 1200.75,
                status = "Shipped",
                itemSummary = "Fresh Crab Curry",
                itemQuantity = 1,
                itemImageUrl = R.drawable.menu2.toString()
            ),
            OrderHistoryItem(
                orderId = "O103",
                restaurantName = "Grand Biriyani House",
                date = "2024-11-15",
                amount = 890.00,
                status = "Pending",
                itemSummary = "Mutton Biriyani",
                itemQuantity = 3,
                itemImageUrl = R.drawable.menu3.toString()
            )
        )
    }
}