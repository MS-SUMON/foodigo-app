package com.example.user

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.user.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val listofLocation = listOf("Mirpur 2","Rupnagar Abashik","BUBT","Mirpur 10","Mirpur 1")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listofLocation)
        val autoCompleteTextView = binding.listofLocation
        autoCompleteTextView.setAdapter(adapter)


    }
}