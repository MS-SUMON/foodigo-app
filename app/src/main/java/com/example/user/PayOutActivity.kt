package com.example.user

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.user.databinding.ActivityPayOutBinding


class PayOutActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.placeMyOrder.setOnClickListener {
            val bottonSheetDialog = CongratesBottomSheet()
            bottonSheetDialog.show(supportFragmentManager,"Text")
        }


    }
}