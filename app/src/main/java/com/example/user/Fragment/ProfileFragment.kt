package com.example.user.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.user.R
import com.example.user.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var isEditable = false  // Edit mode toggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ----------------------------
        // Initially all fields disabled
        // ----------------------------
        setEditable(false)

        // ----------------------------
        // Click-to-Edit Button
        // ----------------------------
        // Adding a temporary "click to edit" TextView at top programmatically
        // Or you can add it in XML
        val clickToEditButton = binding.root.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_save_info)
        // Here, we can use the button itself to toggle editing for demo
        clickToEditButton.setOnClickListener {
            if (!isEditable) {
                isEditable = true
                setEditable(true)
                clickToEditButton.text = "Save Information"
            } else {
                // Save action
                if (validateInput()) {
                    val name = binding.editName.text.toString()
                    val address = binding.editAddress.text.toString()
                    val email = binding.editEmail.text.toString()
                    val phone = binding.editPhone.text.toString()

                    // TODO: Save to Firebase or local storage

                    Toast.makeText(requireContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()

                    // Disable edit mode after save
                    isEditable = false
                    setEditable(false)
                    clickToEditButton.text = "Click Here To Edit"
                }
            }
        }
    }

    // ----------------------------
    // Enable/Disable all EditTexts
    // ----------------------------
    private fun setEditable(enable: Boolean) {
        binding.editName.isEnabled = enable
        binding.editAddress.isEnabled = enable
        binding.editEmail.isEnabled = enable
        binding.editPhone.isEnabled = enable
    }

    // ----------------------------
    // Validation Function
    // ----------------------------
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
