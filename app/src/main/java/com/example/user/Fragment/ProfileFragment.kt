package com.example.user.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.user.LoginActivity
import com.example.user.R
import com.example.user.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var currentUserUid: String? = null

    private var isEditable = false
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        // Initialize Firebase Realtime Database reference
        database = Firebase.database.reference
        currentUserUid = auth.currentUser?.uid

        if (currentUserUid == null) {
            logoutUser()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initial state
        setEditable(false)
        binding.buttonSaveInfo.text = "Update Information"

        loadUserProfile() // Load data from Firebase

        // -------- SAVE/UPDATE BUTTON --------
        binding.buttonSaveInfo.setOnClickListener {
            if (!isEditable) {
                // Currently in 'view' mode, switch to 'edit' mode
                enableEditMode()
            } else {
                // Currently in 'edit' mode, attempt to save
                if (validateInput()) {
                    saveUserProfile() // Save data to Firebase
                }
            }
        }

        // -------- LOGOUT BUTTON --------
        binding.buttonLogout.setOnClickListener {
            logoutUser()
        }

        // -------- PASSWORD VISIBILITY TOGGLE --------
        binding.eyeIcon.setOnClickListener {
            // Only allow toggling if in edit mode and fragment context is available
            if (!isEditable || context == null) return@setOnClickListener

            isPasswordVisible = !isPasswordVisible
            val passwordField = binding.editPassword

            if (isPasswordVisible) {
                // Show password
                passwordField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.eyeIcon.setImageResource(R.drawable.eye_open)
            } else {
                // Hide password
                passwordField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.eyeIcon.setImageResource(R.drawable.eye_close2)
            }
            // Move cursor to the end after changing input type
            passwordField.setSelection(passwordField.text.length)
        }
    }

    private fun loadUserProfile() {
        if (currentUserUid == null) return

        Log.d("ProfileDebug", "Attempting to load data for UID: $currentUserUid")

        // Listen for a single data event
        database.child("user").child(currentUserUid!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Retrieve fields
                    val name = snapshot.child("name").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val phone = snapshot.child("phone").getValue(String::class.java)
                    val address = snapshot.child("address").getValue(String::class.java)
                    val password = snapshot.child("password").getValue(String::class.java)


                    // Populate the EditText fields
                    binding.editName.setText(name)
                    binding.editEmail.setText(email)
                    binding.editPhone.setText(phone ?: "")
                    binding.editAddress.setText(address ?: "")
                    binding.editPassword.setText(password)

                    binding.editEmail.isEnabled = false // Email usually shouldn't be edited via this screen
                } else {
                    Toast.makeText(requireContext(), "User data not found in database!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileFragment", "Database error: ${error.message}")
                Toast.makeText(requireContext(), "Failed to load profile.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserProfile() {
        if (currentUserUid == null) return

        val newPassword = binding.editPassword.text.toString()

        // 1. Prepare updates for Realtime Database (Now includes the password again)
        val updates = HashMap<String, Any>()
        updates["name"] = binding.editName.text.toString()
        updates["address"] = binding.editAddress.text.toString()
        updates["phone"] = binding.editPhone.text.toString()
        // ⚠️ WARNING: Saving password to DB is INSECURE!
        updates["password"] = newPassword

        // Apply Updates to Realtime Database
        database.child("user").child(currentUserUid!!).updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                // Disable edit mode after DB update attempt
                disableEditMode()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to save profile!", Toast.LENGTH_SHORT).show()
                Log.e("ProfileFragment", "Database Save failed: ${it.message}")
            }

        // 2. Update Firebase Authentication Password (Secure way)
        auth.currentUser?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ProfileFragment", "Firebase Auth password updated securely.")
                } else {
                    Log.e("ProfileFragment", "Auth password update failed: ${task.exception?.message}")
                    // Inform the user about the password failure.
                    Toast.makeText(requireContext(), "Password update failed in Firebase Auth. Log out and log in to fix.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun enableEditMode() {
        isEditable = true
        setEditable(true)
        binding.buttonSaveInfo.text = "Save Information"

        // Hide password field and reset visibility icon upon entering edit mode
        isPasswordVisible = false
        binding.editPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.eyeIcon.setImageResource(R.drawable.eye_close2)
        binding.editPassword.requestFocus()
    }

    private fun disableEditMode() {
        isEditable = false
        setEditable(false)
        binding.buttonSaveInfo.text = "Update Information"

        // Hide password field and reset visibility icon after saving
        isPasswordVisible = false
        binding.editPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.eyeIcon.setImageResource(R.drawable.eye_close2)
    }

    private fun setEditable(enable: Boolean) {
        binding.editName.isEnabled = enable
        binding.editAddress.isEnabled = enable
        binding.editPhone.isEnabled = enable
        binding.editPassword.isEnabled = enable
        binding.eyeIcon.isEnabled = enable
        if (!enable) {
            binding.editEmail.isEnabled = false // Email stays disabled
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.editName.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        var isValid = true

        if (name.isEmpty()) { binding.editName.error = "Name cannot be empty"; isValid = false }
        if (address.isEmpty()) { binding.editAddress.error = "Address cannot be empty"; isValid = false }
        if (email.isEmpty()) { binding.editEmail.error = "Email cannot be empty"; isValid = false }
        if (phone.isEmpty()) { binding.editPhone.error = "Phone cannot be empty"; isValid = false }
        if (password.isEmpty()) { binding.editPassword.error = "Password cannot be empty"; isValid = false }
        else if (password.length < 6) { binding.editPassword.error = "Password must be at least 6 characters"; isValid = false }

        return isValid
    }

    private fun logoutUser() {
        auth.signOut()
        // Clear local session data (if used)
        val sharedPref = requireActivity().getSharedPreferences("user_session", 0)
        sharedPref.edit().clear().apply()

        // Navigate to Login Activity and clear back stack
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}