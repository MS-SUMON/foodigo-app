package com.example.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.user.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)

        // Using binding for consistency
        binding.bottomNavigationView2.setupWithNavController(navController)

        binding.notificationBell.setOnClickListener {
            // CORRECT WAY to show a BottomSheetDialogFragment
            val notificationSheet = NotificationBottomFragment()
            notificationSheet.show(supportFragmentManager, "NotificationBottomSheet")
        }

    }
}