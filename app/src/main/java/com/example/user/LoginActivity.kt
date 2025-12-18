package com.example.user

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.seller.MainActivitySeller
import com.example.admin.MainActivityAdmin
import com.example.user.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var email: String
    private lateinit var password: String
    private var isPasswordVisible = false
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 2. Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        // "Don't have account" button
        binding.donthavebutton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // Login Button
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim() // Changed to local 'val'
            val password = binding.passwordLogin.text.toString().trim() // Changed to local 'val'

            // Empty Check
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Password strength check
            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            performLogin(email, password)
        }

        binding.passwordLogin.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val editText = binding.passwordLogin
                val drawableEnd = editText.compoundDrawables[DRAWABLE_END]

                if (drawableEnd != null && event.rawX >= (editText.right -
                            drawableEnd.bounds.width() - editText.paddingEnd)) {
                    isPasswordVisible = !isPasswordVisible

                    val inputType = if (isPasswordVisible)
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                    editText.inputType = inputType
                    editText.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.lock_01, 0, if (isPasswordVisible) R.drawable.eye_open else R.drawable.eye_close2, 0
                    )
                    editText.setSelection(editText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Login Successful.", Toast.LENGTH_SHORT).show()

                    if (email.startsWith("admin", ignoreCase = true) && email.contains("@")) {
                        startActivity(Intent(this, MainActivityAdmin::class.java))
                        finish()
                    }
                    else if (email.startsWith("seller", ignoreCase = true) && email.contains("@")) {
                        startActivity(Intent(this, MainActivitySeller::class.java))
                        finish()
                    }
                    else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(baseContext, "Authentication Failed. Please check your Email and Password.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun isStrongPassword(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters"
        }
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=])(?=\\S+\$).{8,}\$")

        if (!regex.matches(password)) {
            return "Password must contain digit, uppercase, lowercase & special character (!@#\$%^&+=)"
        }
        return null
    }
}