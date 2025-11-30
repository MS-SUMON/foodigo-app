package com.example.seller

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.R
import com.example.user.databinding.ActivityProfilePageSellerBinding

class ProfilePage : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePageSellerBinding
    private var isEditable = false
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfilePageSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BACK Button
        binding.backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Initially, disable all fields and set button text to "Update"
        setEditable(false)
        binding.sellerLoginBtn.text = "Update Information"


        // -------- SAVE BUTTON --------
        binding.sellerLoginBtn.setOnClickListener {
            if (!isEditable) {
                // Enable edit mode on first click
                enableEditMode()
            } else {
                // Save data on second click
                if (validateInput()) {
                    val name = binding.editName.text.toString()
                    val address = binding.editAddress.text.toString()
                    val email = binding.editEmail.text.toString()
                    val phone = binding.editPhone.text.toString()
                    val password = binding.editPassword.text.toString()

                    // TODO: Save profile to database or server

                    Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                    disableEditMode()
                }
            }
        }

        // -------- PASSWORD VISIBILITY --------
        binding.eyeIcon.setOnClickListener {
            if (!isEditable) return@setOnClickListener // Only toggle when editing

            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.editPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.eyeIcon.setImageResource(R.drawable.eye_open)
            } else {
                binding.editPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.eyeIcon.setImageResource(R.drawable.eye_close2)
            }
            binding.editPassword.setSelection(binding.editPassword.text.length)
        }
    }

    private fun enableEditMode() {
        isEditable = true
        setEditable(true)
        binding.sellerLoginBtn.text = "Save Information"

        // Reset password field to hidden
        isPasswordVisible = false
        binding.editPassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.eyeIcon.setImageResource(R.drawable.eye_close2)
        binding.editPassword.setSelection(binding.editPassword.text.length)
    }

    private fun disableEditMode() {
        isEditable = false
        setEditable(false)
        binding.sellerLoginBtn.text = "Update Information"

        // Reset password field to hidden
        isPasswordVisible = false
        binding.editPassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.eyeIcon.setImageResource(R.drawable.eye_close2)
        binding.editPassword.setSelection(binding.editPassword.text.length)
    }

    private fun setEditable(enable: Boolean) {
        binding.editName.isEnabled = enable
        binding.editAddress.isEnabled = enable
        binding.editEmail.isEnabled = enable
        binding.editPhone.isEnabled = enable
        binding.editPassword.isEnabled = enable
        binding.eyeIcon.isEnabled = enable
    }

    private fun validateInput(): Boolean {
        val name = binding.editName.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if (name.isEmpty()) { binding.editName.error = "Name cannot be empty"; return false }
        if (address.isEmpty()) { binding.editAddress.error = "Address cannot be empty"; return false }
        if (email.isEmpty()) { binding.editEmail.error = "Email cannot be empty"; return false }
        if (phone.isEmpty()) { binding.editPhone.error = "Phone cannot be empty"; return false }
        if (password.isEmpty()) { binding.editPassword.error = "Password cannot be empty"; return false }

        return true
    }
}
