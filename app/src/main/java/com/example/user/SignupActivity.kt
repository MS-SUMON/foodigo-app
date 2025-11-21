package com.example.user

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.user.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // --- Navigate to LoginActivity ---
        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // --- Password eye toggle ---
        binding.editTextTextPassword2.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val editText = binding.editTextTextPassword2
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

        // --- Create Account button ---
        binding.button6.setOnClickListener {
            val name = binding.editTextText.text.toString().trim()
            val email = binding.editTextTextEmailAddress2.text.toString().trim()
            val password = binding.editTextTextPassword2.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // For now just show a toast (no database yet)
            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // --- Strong password check ---
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
