package com.example.user

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.user.databinding.ActivitySignupBinding
import com.example.user.modelpackageforuser.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var isPasswordVisible = false
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.creatButton.setOnClickListener {
            val userName = binding.name.text.toString().trim()
            val email = binding.emailOrPhone.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            createAccount(userName, email, password)
        }

        setupPasswordToggle()
    }

    private fun setupPasswordToggle() {
        binding.password.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val editText = binding.password
                val drawableEnd = editText.compoundDrawables[DRAWABLE_END]
                if (drawableEnd != null && event.rawX >= (editText.right - drawableEnd.bounds.width() - editText.paddingEnd)) {
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
    private fun createAccount(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserData(userName, email, password)
            } else {
                Toast.makeText(this, "Account Creation Failed: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
                Log.e("SignupActivity", "createAccount: Failure", task.exception)
            }
        }
    }
    private fun saveUserData(userName: String, email: String, password: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val user = UserModel(userName, email, password)
            val userID: String = currentUser.uid

            database.child("user").child(userID).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error saving profile data.", Toast.LENGTH_SHORT).show()
                    Log.e("SignupActivity", "Failed to save user data", it)
                    // Optional: Delete user from Auth if data saving fails
                    currentUser.delete()
                }
        }
    }

    private fun isStrongPassword(password: String): String? {
        if (password.length < 8) return "Password must be at least 8 characters"
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=])(?=\\S+\$).{8,}\$")
        if (!regex.matches(password)) return "Password must contain digit, uppercase, lowercase & special character (!@#\$%^&+=)"
        return null
    }
}