package com.example.user

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.user.databinding.ActivityLoginBinding
import com.example.seller.MainActivitySeller
import com.example.admin.MainActivityAdmin
import com.google.firebase.auth.FirebaseAuth // <--- Firebase Auth Import

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var isPasswordVisible = false
    private lateinit var auth: FirebaseAuth // <--- 1. FirebaseAuth Instance Declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 2. Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance()

        // "Don't have account" button
        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // Login button
        binding.loginbtn.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- Password strength check ---
            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                // If password fails the strong password check, display the error and stop.
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            performLogin(email, password)
        }

        binding.editTextTextPassword.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val editText = binding.editTextTextPassword
                val drawableEnd = editText.compoundDrawables[DRAWABLE_END]
                if (drawableEnd != null) {
                    if (event.rawX >= (editText.right - drawableEnd.bounds.width() - editText.paddingEnd)) {
                        isPasswordVisible = !isPasswordVisible
                        if (isPasswordVisible) {
                            editText.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            editText.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.lock_01,
                                0,
                                R.drawable.eye_open,
                                0
                            )
                        } else {
                            editText.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            editText.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.lock_01,
                                0,
                                R.drawable.eye_close2,
                                0
                            )
                        }
                        editText.setSelection(editText.text.length)
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    // --- NEW FUNCTION: Database/Firebase Login Check ---
    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful! Now determine the user role
                    Toast.makeText(baseContext, "Login Successful.", Toast.LENGTH_SHORT).show()

                    // 1. Check for Admin Role
                    if (email.startsWith("admin", ignoreCase = true) && email.contains("@")) {
                        val adminIntent = Intent(this, MainActivityAdmin::class.java)
                        startActivity(adminIntent)
                        finish()
                    }
                    // 2. Check for Seller Role
                    else if (email.startsWith("seller", ignoreCase = true) && email.contains("@")) {
                        val sellerIntent = Intent(this, MainActivitySeller::class.java)
                        startActivity(sellerIntent)
                        finish()
                    }
                    // 3. Default to User Role
                    else {
                        val userIntent = Intent(this, MainActivity::class.java)
                        startActivity(userIntent)
                        finish()
                    }

                } else {
                    // Login failed (e.g., incorrect email or password)
                    Toast.makeText(baseContext, "Authentication Failed. Please check your Email and Password.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun isStrongPassword(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long."
        }
        val passwordRegex = Regex(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=])(?=\\S+\$).{8,}\$"
        )
        if (!passwordRegex.matches(password)) {
            return "Password must contain at least 1 digit, 1 lowercase, 1 uppercase, and 1 special character (!@#\$%^&+=)."
        }
        return null
    }
}