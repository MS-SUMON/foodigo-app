package com.example.user

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.model.UserModel
import com.example.user.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlin.math.log

class SignupActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //initialization Firebase Auth
        auth = Firebase.auth
        //initialize Firebase database
        database = Firebase.database.reference


        // --- Navigate to LoginActivity ---
        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // --- Password eye toggle ---
        binding.password.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val editText = binding.password
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
        binding.creatButton.setOnClickListener {
            //Get text from edittext
            userName = binding.name.text.toString().trim()
            //nameOfRestaurant = binding.restaurantName.text.toString().trim()
            email = binding.emailOrPhone.text.toString().trim()
            password = binding.password.text.toString().trim()


            //val name = binding.name.text.toString().trim()
            //val email = binding.emailOrPhone.text.toString().trim()
            //val password = binding.password.text.toString().trim()

            if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                createAccount(email,password)
            }

            val passwordError = isStrongPassword(password)
            if (passwordError != null) {
                Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // For now just show a toast (no database yet)
            //Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure",task.exception)
            }
        }
    }
    // Save Data into Database
    fun saveUserData() {
        //Get text from edittext
        userName = binding.name.text.toString().trim()
        //nameOfRestaurant = binding.restaurantName.text.toString().trim()
        email = binding.emailOrPhone.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(userName,email,password)
        val userID : String = FirebaseAuth.getInstance().currentUser!!.uid
        // save user data Firebase database
        database.child("user").child(userID).setValue(user)
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
