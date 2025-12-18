package com.example.seller

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.R
import com.example.user.databinding.ActivityProfilePageSellerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilePage : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePageSellerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var isEditable = false
    private var isPasswordVisible = false
    private var currentUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfilePageSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        currentUserId = auth.currentUser?.uid

        // Back button
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Initially locked
        setEditable(false)
        binding.sellerLoginBtn.text = "Update Information"

        // 🔹 Load profile from DB
        loadProfileFromDatabase()

        // Update / Save button
        binding.sellerLoginBtn.setOnClickListener {
            if (!isEditable) {
                enableEditMode()
            } else {
                if (validateInput()) {
                    updateProfileInDatabase()
                }
            }
        }

        // Password visibility
        binding.eyeIcon.setOnClickListener {
            if (!isEditable) return@setOnClickListener

            isPasswordVisible = !isPasswordVisible
            binding.editPassword.inputType =
                if (isPasswordVisible)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            binding.eyeIcon.setImageResource(
                if (isPasswordVisible) R.drawable.eye_open else R.drawable.eye_close2
            )
            binding.editPassword.setSelection(binding.editPassword.text.length)
        }
    }

    // 🔹 Load data from DB
    private fun loadProfileFromDatabase() {
        if (currentUserId == null) return

        database.child("user").child(currentUserId!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.editName.setText(snapshot.child("name").value.toString())
                        binding.editEmail.setText(snapshot.child("email").value.toString())
                        binding.editPassword.setText(snapshot.child("password").value.toString())

                        // NEW
                        binding.editAddress.setText(snapshot.child("address").value?.toString() ?: "")
                        binding.editPhone.setText(snapshot.child("phone").value?.toString() ?: "")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ProfilePage, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // 🔹 Update DB
    private fun updateProfileInDatabase() {
        if (currentUserId == null) return

        val updates = HashMap<String, Any>()
        updates["name"] = binding.editName.text.toString().trim()
        updates["email"] = binding.editEmail.text.toString().trim()
        updates["password"] = binding.editPassword.text.toString().trim()

        // NEW
        updates["address"] = binding.editAddress.text.toString().trim()
        updates["phone"] = binding.editPhone.text.toString().trim()

        database.child("user").child(currentUserId!!)
            .updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                disableEditMode()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Update Failed!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun enableEditMode() {
        isEditable = true
        setEditable(true)
        binding.sellerLoginBtn.text = "Save Information"
    }

    private fun disableEditMode() {
        isEditable = false
        setEditable(false)
        binding.sellerLoginBtn.text = "Update Information"
    }

    private fun setEditable(enable: Boolean) {
        binding.editName.isEnabled = enable
        binding.editEmail.isEnabled = enable
        binding.editPassword.isEnabled = enable

        // NEW
        binding.editAddress.isEnabled = enable
        binding.editPhone.isEnabled = enable

        binding.eyeIcon.isEnabled = enable
    }

    private fun validateInput(): Boolean {
        if (binding.editName.text.isNullOrEmpty()) return false
        if (binding.editEmail.text.isNullOrEmpty()) return false
        if (binding.editPassword.text.isNullOrEmpty()) return false
        return true
    }
}
