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
import com.example.user.R // Assuming R is in com.example.user

class SuspendAccountsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SuspendedAccountAdapter
    private val suspendedAccounts = mutableListOf<AccountItem>()

    // 3. FIX: Define the constant for passing data back
    companion object {
        const val KEY_RESUMED_ACCOUNT = "RESUMED_ACCOUNT_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge and ViewCompat code removed for brevity, assume they are still there
        setContentView(R.layout.activity_suspend_accounts)

        // Back button logic
        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        // RecyclerView setup
        recyclerView = findViewById(R.id.recycler_view_suspended_accounts)

        suspendedAccounts.addAll(createSampleAccounts())

        // Setup Adapter with the handleResumeAction logic
        adapter = SuspendedAccountAdapter(suspendedAccounts) { account ->
            handleResumeAction(account) // Calling the defined function
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // 2. FIX: Define the missing function
    private fun handleResumeAction(account: AccountItem) {
        // 1. Remove the item from the local list
        val position = suspendedAccounts.indexOf(account)
        if (position != -1) {
            suspendedAccounts.removeAt(position)
            // 2. Notify the adapter to refresh the list appearance
            adapter.notifyItemRemoved(position)

            // 3. Prepare the result to send back to the calling Activity
            val resultIntent = Intent()
            resultIntent.putExtra(KEY_RESUMED_ACCOUNT, account.name)
            setResult(Activity.RESULT_OK, resultIntent)

            Toast.makeText(this, "${account.name} has been set to Active.", Toast.LENGTH_SHORT).show()
        }

        // If the list is empty after removal, automatically close the activity
        if (suspendedAccounts.isEmpty()) {
            finish()
        }
    }

    private fun createSampleAccounts(): List<AccountItem> {
        // NOTE: Ensure R.drawable.menu1, etc., exist
        return listOf(
            AccountItem(id = "ACC1", name = "Spacy fresh crab", owner = "Owner A (Sifat)", imageResId = R.drawable.menu1),
            AccountItem(id = "ACC2", name = "Royal Burger", owner = "Owner B (John)", imageResId = R.drawable.menu2),
            AccountItem(id = "ACC3", name = "Ami's Kitchen", owner = "Owner C (Ami)", imageResId = R.drawable.menu3)
        )
    }
}