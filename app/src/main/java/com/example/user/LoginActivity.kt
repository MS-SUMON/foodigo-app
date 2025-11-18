package com.example.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.user.databinding.ActivityLoginBinding
import com.example.seller.MainActivitySeller

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginbtn.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (email.startsWith("seller", ignoreCase = true) && email.contains("@")) {
                val adminIntent = Intent(this, MainActivitySeller::class.java)
                startActivity(adminIntent)
                finish()

            } else {
                val userIntent = Intent(this, MainActivity::class.java)
                startActivity(userIntent)
                finish()
            }
        }
    }

    fun isStrongPassword(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long."
        }
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=])(?=\\S+\$).{8,}\$")

        if (!passwordRegex.matches(password)) {
            return "Password must contain at least 1 digit, 1 lowercase, 1 uppercase, and 1 special character (!@#\$%^&+=)."
        }

        return null
    }
}