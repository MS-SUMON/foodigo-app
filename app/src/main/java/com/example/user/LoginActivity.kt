package com.example.user


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.model.UserModel
import com.example.seller.MainActivitySeller
import com.example.user.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


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


        auth = Firebase.auth
        database = Firebase.database.reference


        // New Account Page
        binding.donthavebutton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }


        // Login Button
        binding.loginButton.setOnClickListener {
            email = binding.email.text.toString().trim()
            password = binding.passwordLogin.text.toString().trim()


            // Empty Check
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Email Format Check
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Password Strength Check
            val passError = isStrongPassword(password)
            if (passError != null) {
                Toast.makeText(this, passError, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Try Login
            loginUser(email, password)
        }


        // Password Visibility Toggle
        binding.passwordLogin.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val editText = binding.passwordLogin
                val drawableEnd = editText.compoundDrawables[DRAWABLE_END]


                if (drawableEnd != null && event.rawX >= (editText.right -
                            drawableEnd.bounds.width() - editText.paddingEnd)) {


                    isPasswordVisible = !isPasswordVisible


                    if (isPasswordVisible) {
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock_01, 0, R.drawable.eye_open, 0
                        )
                    } else {
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock_01, 0, R.drawable.eye_close2, 0
                        )
                    }
                    editText.setSelection(editText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }




    // Login User Function
    private fun loginUser(email: String, password: String) {


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->


            if (task.isSuccessful) {


                val userId = auth.currentUser!!.uid


                // Check if user exists in database
                database.child("user").child(userId).get().addOnSuccessListener {


                    if (it.exists()) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        navigateUser(email)


                    } else {
                        Toast.makeText(this, "Please! Create an Account", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, SignupActivity::class.java))
                    }
                }


            } else {
                Toast.makeText(this, "Please! Create an Account", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SignupActivity::class.java))
            }
        }
    }


    // User Navigation According To Email
    private fun navigateUser(email: String) {
        if (email.startsWith("seller", ignoreCase = true)) {
            startActivity(Intent(this, MainActivitySeller::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
    // email format
    private fun isValidEmail(email: String): Boolean {
        // small letter allow
        if (email != email.lowercase()) return false


        val emailRegex = Regex("^[a-z0-9]+@[a-z0-9]+\\.(com|net|org|bd)$")
        return emailRegex.matches(email)
    }




    // Password Strength Check
    private fun isStrongPassword(password: String): String? {


        if (password.length < 8) {
            return "Password must be at least 8 characters"
        }


        val regex = Regex(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=])(?=\\S+\$).{8,}\$"
        )


        if (!regex.matches(password)) {
            return "Password must contain digit, uppercase, lowercase & special character"
        }


        return null
    }
}
