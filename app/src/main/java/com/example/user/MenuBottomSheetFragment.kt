package com.example.user

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user.adapter.MenuAdapter
import com.example.user.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        // --- Define all required data lists ---
        val menuFoodName = listOf("Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo","Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo")
        val menuItemPrice = listOf("$5", "$6", "$8", "$9", "$10", "$10","$5", "$6", "$8", "$9", "$10", "$10")

        // Define the Owner Names list (must match the size of other lists)
        val menuOwnerName = listOf("Owner A", "Owner B", "Owner C", "Owner D", "Owner E", "Owner F","Owner A", "Owner B", "Owner C", "Owner D", "Owner E", "Owner F") // 12 items

        val menuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu2,
            R.drawable.menu3
        )

        // --- FIX: Pass all FIVE arguments to the MenuAdapter constructor ---
        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuItemPrice),
            ArrayList(menuImage),
            ArrayList(menuOwnerName), // 1. Pass the new Owner Name list
            requireContext()          // 2. Pass the Context (renamed 'context' in adapter)
        )

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        return binding.root

    }

    companion object {

    }
}