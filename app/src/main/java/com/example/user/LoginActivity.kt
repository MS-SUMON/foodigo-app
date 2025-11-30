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
import com.example.user.databinding.ActivityLoginBinding // 💡 MISSING BINDING IMPORT ADDED
import com.google.firebase.auth.FirebaseAuth // 💡 MISSING IMPORT ADDED
import com.google.firebase.database.DatabaseReference // 💡 MISSING IMPORT ADDED
import com.google.firebase.database.ktx.database // For Firebase.database
import com.google.firebase.ktx.Firebase // For Firebase.auth and Firebase.database

class LoginActivity : AppCompatActivity() {

    // Removed the initial redeclarations. Use only one set of declarations.
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference // Not strictly needed for *login*, but kept as it was in original
    private lateinit var email: String // Initialized later, can be removed if not needed outside performLogin
    private lateinit var password: String // Initialized later, can be removed if not needed outside performLogin
    private var isPasswordVisible = false // 💡 REDECLARATION REMOVED

    // Initialize binding using lazy delegate
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    // 💡 REMOVED REDECLARATIONS AND KEPT THE ONES ABOVE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 2. Initialize FirebaseAuth instance and Database
        auth = FirebaseAuth.getInstance() // Standard way to init Auth if not using KTX delegate
        database = Firebase.database.reference // Initializing database reference

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

            // --- Password strength check ---
            // 💡 Ensure you are calling the complete isStrongPassword function
            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            performLogin(email, password)
        }

        // Password Visibility Toggle
        // 💡 The password field ID should be checked. The code uses `binding.editTextTextPassword`
        // in setOnTouchListener, but then uses `binding.passwordLogin` inside the listener.
        // I will standardize on `binding.passwordLogin` for the listener.
        binding.passwordLogin.setOnTouchListener { v, event -> // 💡 STANDARDIZED ID TO passwordLogin
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
                    // Login successful! Now determine the user role
                    Toast.makeText(baseContext, "Login Successful.", Toast.LENGTH_SHORT).show()

                    // Check for Admin Role, Seller Role, or Default User Role
                    // NOTE: This role determination via email prefix is NOT secure and should be done via a database lookup!
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
                    // Login failed (e.g., incorrect email or password)
                    Toast.makeText(baseContext, "Authentication Failed. Please check your Email and Password.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    // 💡 REMOVED INCOMPLETE isStrongPassword FUNCTION AND KEPT THE COMPLETE ONE BELOW

    // 💡 The 'navigateUser' and 'isValidEmail' functions are unused in the provided logic, so they can be removed or kept if needed elsewhere.

    // Password Strength Check (Complete version)
    private fun isStrongPassword(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters"
        }
        // Requires digit, lowercase, uppercase, and special character
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=])(?=\\S+\$).{8,}\$")

        if (!regex.matches(password)) {
            return "Password must contain digit, uppercase, lowercase & special character (!@#\$%^&+=)"
        }
        return null
    }
}