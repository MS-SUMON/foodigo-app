package com.example.admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.adapter.SuspendedAccountAdapter
import com.example.admin.datamodel.AccountItem
import com.example.user.R

class SuspendAccountsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SuspendedAccountAdapter
    private val suspendedAccounts = mutableListOf<AccountItem>()
    companion object {
        const val KEY_RESUMED_ACCOUNT = "RESUMED_ACCOUNT_ITEM"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suspend_accounts)

        // Back button
        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        // RecyclerView setup
        recyclerView = findViewById(R.id.recycler_view_suspended_accounts)

        suspendedAccounts.addAll(createSampleAccounts())

        adapter = SuspendedAccountAdapter(suspendedAccounts) { account ->
            handleResumeAction(account) // Calling the defined function
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    private fun handleResumeAction(account: AccountItem) {
        // 1. Remove the item from the local list
        val position = suspendedAccounts.indexOf(account)
        if (position != -1) {
            suspendedAccounts.removeAt(position)
            adapter.notifyItemRemoved(position)
            val resultIntent = Intent()
            resultIntent.putExtra(KEY_RESUMED_ACCOUNT, account.name)
            setResult(Activity.RESULT_OK, resultIntent)

            Toast.makeText(this, "${account.name} has been set to Active.", Toast.LENGTH_SHORT).show()
        }
        if (suspendedAccounts.isEmpty()) {
            finish()
        }
    }

    private fun createSampleAccounts(): List<AccountItem> {
        return listOf(
            AccountItem(id = "ACC1", name = "Spacy fresh crab", owner = "Owner A (Sifat)", imageResId = R.drawable.menu1),
            AccountItem(id = "ACC2", name = "Royal Burger", owner = "Owner B (John)", imageResId = R.drawable.menu2),
            AccountItem(id = "ACC3", name = "Ami's Kitchen", owner = "Owner C (Ami)", imageResId = R.drawable.menu3)
        )
    }
}