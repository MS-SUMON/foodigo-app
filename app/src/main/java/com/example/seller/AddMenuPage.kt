package com.example.seller

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.user.R

class AddMenuPage : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    private lateinit var uploadBtn: ImageButton
    private lateinit var previewImage: ImageView
    private lateinit var itemName: EditText
    private lateinit var itemPrice: EditText
    private lateinit var shortDesc: EditText
    private lateinit var ingredients: EditText
    private lateinit var addItemButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_menu_page)

        // For edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Hooks
        backBtn = findViewById(R.id.back_button)
        uploadBtn = findViewById(R.id.upload_button)
        previewImage = findViewById(R.id.item_image_preview)

        itemName = findViewById(R.id.edit_item_name)
        itemPrice = findViewById(R.id.edit_item_price)
        shortDesc = findViewById(R.id.edit_short_description)
        ingredients = findViewById(R.id.edit_ingredients)

        addItemButton = findViewById(R.id.add_item_button)

        // Back button
        backBtn.setOnClickListener {
            onBackPressed()
        }

        // Upload button (later we can add gallery picker)
        uploadBtn.setOnClickListener {
            Toast.makeText(this, "Upload Image Clicked", Toast.LENGTH_SHORT).show()
        }

        // Add Item Button
        addItemButton.setOnClickListener {
            addMenuItem()
        }
    }

    private fun addMenuItem() {

        val name = itemName.text.toString().trim()
        val price = itemPrice.text.toString().trim()
        val desc = shortDesc.text.toString().trim()
        val ingr = ingredients.text.toString().trim()

        // Validation
        if (name.isEmpty()) {
            itemName.error = "Enter item name"
            return
        }

        if (price.isEmpty()) {
            itemPrice.error = "Enter item price"
            return
        }

        if (desc.isEmpty()) {
            shortDesc.error = "Enter short description"
            return
        }

        if (ingr.isEmpty()) {
            ingredients.error = "Enter ingredients"
            return
        }

        // If everything is ok
        Toast.makeText(this, "Item Added Successfully ✔", Toast.LENGTH_LONG).show()

        // Clear fields (optional)
        itemName.setText("")
        itemPrice.setText("")
        shortDesc.setText("")
        ingredients.setText("")
    }
}
