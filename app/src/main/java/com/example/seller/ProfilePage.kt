package com.example.seller


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.databinding.ActivityProfilePageBinding

class ProfilePage : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePageBinding
    private var isEditable = false   // Edit mode toggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BACK Button
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Toggle EDIT Mode
        binding.clickToEditButton.setOnClickListener {
            isEditable = !isEditable
            setEditable(isEditable)

            binding.clickToEditButton.text =
                if (isEditable) "Editing..." else "Click Here To Edit"
        }

        // SAVE Information Button
        binding.sellerLoginBtn.setOnClickListener {
            if (validateInput()) {
                val name = binding.editName.text.toString()
                val address = binding.editAddress.text.toString()
                val email = binding.editEmail.text.toString()
                val phone = binding.editPhone.text.toString()


                Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()

                // Disable edit mode after save
                isEditable = false
                setEditable(false)
                binding.clickToEditButton.text = "Click Here To Edit"
            }
        }
        // Start with all EditTexts disabled
        setEditable(false)
    }

    // Enable/Disable All EditTexts

    private fun setEditable(enable: Boolean) {
        binding.editName.isEnabled = enable
        binding.editAddress.isEnabled = enable
        binding.editEmail.isEnabled = enable
        binding.editPhone.isEnabled = enable
    }

    // VALIDATION FUNCTION
    private fun validateInput(): Boolean {

        val name = binding.editName.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()

        if (name.isEmpty()) {
            binding.editName.error = "Name cannot be empty"
            return false
        }
        if (address.isEmpty()) {
            binding.editAddress.error = "Address cannot be empty"
            return false
        }
        if (email.isEmpty()) {
            binding.editEmail.error = "Email cannot be empty"
            return false
        }
        if (phone.isEmpty()) {
            binding.editPhone.error = "Phone cannot be empty"
            return false
        }

        return true
    }
}
