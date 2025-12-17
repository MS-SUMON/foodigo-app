package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.R
import com.google.android.material.imageview.ShapeableImageView

class SellerProfileActivityForAdmin : AppCompatActivity() {

    // Header
    private lateinit var btnBack: ImageButton
    private lateinit var tvTitleSellerProfile: TextView

    // Profile section
    private lateinit var imgSellerProfile: ShapeableImageView
    private lateinit var tvSellerName: TextView // Business Name (Header)
    private lateinit var btnActive: Button

    // Seller info
    private lateinit var tvName: TextView // Owner Name (Details Section: sallername)
    private lateinit var tvAddress: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView

    // Grid cards
    private lateinit var tvCurrentBalance: TextView
    private lateinit var tvPendingPayout: TextView
    private lateinit var tvCompletedOrders: TextView
    private lateinit var tvCommissionPaid: TextView

    // Tap to Pay element
    private lateinit var tvTapToPay: TextView

    // Commission
    private lateinit var tvCommissionValue: TextView
    private lateinit var tvChangeRate: TextView

    // Buttons
    private lateinit var btnViewMenu: Button
    private lateinit var btnSuspend: Button

    // Local state for commission rate editing
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_profile_for_admin)

        // --- View Initialization ---
        btnBack = findViewById(R.id.btn_back)
        tvTitleSellerProfile = findViewById(R.id.tv_title_seller_profile)
        imgSellerProfile = findViewById(R.id.img_seller_profile)
        tvSellerName = findViewById(R.id.tv_seller_name)
        btnActive = findViewById(R.id.btn_active)
        tvName = findViewById(R.id.sallername)
        tvAddress = findViewById(R.id.selleraddresstext)
        tvEmail = findViewById(R.id.selleraddress)
        tvPhone = findViewById(R.id.phone_number)
        tvCurrentBalance = findViewById(R.id.tv_current_balance)
        tvPendingPayout = findViewById(R.id.tv_pending_payout)
        tvCompletedOrders = findViewById(R.id.tv_completed_orders)
        tvCommissionPaid = findViewById(R.id.tv_commission_paid)
        tvTapToPay = findViewById(R.id.tv_tap_to_pay)
        tvCommissionValue = findViewById(R.id.tv_commission_value)
        tvChangeRate = findViewById(R.id.tv_change_rate)
        btnViewMenu = findViewById(R.id.btn_view_menu)
        btnSuspend = findViewById(R.id.btn_suspend)

        // --- Defensive and Style Fixes ---
        tvCommissionValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f)
        tvCommissionValue.isFocusable = false
        tvCommissionValue.isClickable = true

        // Load static data first (excluding names)
        loadStaticSellerData()

        // --- ✅ FIX: Get the dynamic names from the Intent and set them ---
        val sellerNameFromIntent = intent.getStringExtra("SELLER_NAME")
        val ownerNameFromIntent = intent.getStringExtra("OWNER_NAME")

        // Set Business Name (Header)
        if (sellerNameFromIntent != null) {
            tvSellerName.text = sellerNameFromIntent
        }

        // Set Owner Name (Details section: sallername)
        if (ownerNameFromIntent != null) {
            tvName.text = ownerNameFromIntent
        } else {
            // Fallback to Seller Name if Owner Name is missing
            tvName.text = sellerNameFromIntent ?: "N/A"
        }
        // ----------------------------------------------------------------

        // --- Click Listeners ---
        btnBack.setOnClickListener {
            finish()
        }

        // Suspend / Resume Toggle Logic
        btnSuspend.setOnClickListener {
            if (btnActive.text == "Active") {
                // Current state is Active -> Change to Suspended
                btnSuspend.text = "Resume"
                btnActive.text = "Suspended"
                btnActive.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, theme))
                btnSuspend.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark, theme))
            } else {
                // Current state is Suspended -> Change to Active
                btnActive.text = "Active"
                btnSuspend.text = "Suspend"
                btnActive.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark, theme))
                btnSuspend.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, theme))
            }
        }

        btnViewMenu.setOnClickListener {
            val intent = Intent(this, SellerMenuActivityAdmin::class.java)
            intent.putExtra("sellerName", tvSellerName.text.toString())
            startActivity(intent)
        }

        // Tap to Pay Conditional Logic
        addTapToPayListener()

        // Commission Rate Change Logic
        tvChangeRate.setOnClickListener {
            if (!isEditing) {
                // IF: View Mode -> Switch to EDIT MODE
                isEditing = true
                tvChangeRate.text = "Save Changes"

                val originalLayoutParams = tvCommissionValue.layoutParams
                val currentRate = tvCommissionValue.text.toString().replace("%", "")
                val input = EditText(this)
                input.setText(currentRate)
                input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                input.layoutParams = originalLayoutParams
                input.setBackgroundResource(android.R.color.transparent)
                input.isFocusableInTouchMode = true
                input.isFocusable = true

                val parent = tvCommissionValue.parent as ViewGroup
                val index = parent.indexOfChild(tvCommissionValue)
                parent.removeView(tvCommissionValue)
                parent.addView(input, index)

                input.requestFocus()
                tvCommissionValue.tag = Pair(input, originalLayoutParams)
            } else {
                // ELSE: Edit Mode -> Switch back to VIEW MODE
                isEditing = false
                tvChangeRate.text = "Change Rate"

                @Suppress("UNCHECKED_CAST")
                val editState = tvCommissionValue.tag as Pair<EditText, ViewGroup.LayoutParams>

                val (input, originalLayoutParams) = editState
                val newRate = input.text.toString().ifEmpty { "5" }

                val parent = input.parent as ViewGroup
                val index = parent.indexOfChild(input)
                parent.removeView(input)

                tvCommissionValue.text = "$newRate%"
                tvCommissionValue.layoutParams = originalLayoutParams
                tvCommissionValue.isFocusable = false
                tvCommissionValue.isFocusableInTouchMode = false
                parent.addView(tvCommissionValue, index)

                Toast.makeText(this, "Commission rate updated to $newRate%", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- Helper Functions ---

    private fun addTapToPayListener() {
        tvTapToPay.setOnClickListener {
            val pendingPayoutString = tvPendingPayout.text.toString()
            val payoutAmount = pendingPayoutString.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: 0.0

            if (payoutAmount > 0.0) {
                val sellerName = tvSellerName.text.toString()
                val intent = Intent(this, FinanceAndPayoutActivity::class.java)
                intent.putExtra("sellerName", sellerName)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No pending payout for this seller.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Loads hardcoded non-name data
    private fun loadStaticSellerData() {
        // Name fields are handled by the Intent logic in onCreate()

        tvAddress.text = "lorem ipsum, road 360, Dhaka"
        tvEmail.text = "loremipsum@gmail.com"
        tvPhone.text = "123456789"

        tvCurrentBalance.text = "5000TK"
        tvPendingPayout.text = "1000TK"
        tvCompletedOrders.text = "300"
        tvCommissionPaid.text = "2000TK"
        tvCommissionValue.text = "5%"
    }
}