package com.example.user.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user.R
import com.example.user.adapter.MenuAdapter
import com.example.user.databinding.FragmentSearchBinding
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter

    // Define the full original data sets, including Owner Names
    private val originalMenuFoodName = listOf("Burger", "Momo", "Momo", "Momo", "Momo", "Momo","Momo", "Sandwich", "Momo", "Item", "Sandwich", "Momo")
    private val originalMenuItemPrice = listOf("$5", "$6", "$8", "$9", "$10", "$10","$5", "$6", "$8", "$9", "$10", "$10")
    private val originalMenuImage = listOf(
        R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4, R.drawable.menu2, R.drawable.menu3,
        R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4, R.drawable.menu2, R.drawable.menu3
    )
    private val originalMenuOwnerName = listOf("Owner A", "Owner B", "Owner C", "Owner D", "Owner E", "Owner F","Owner A", "Owner B", "Owner C", "Owner D", "Owner E", "Owner F")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuItemPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()
    private val filteredMenuOwnerName = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = MenuAdapter(
            filteredMenuFoodName,
            filteredMenuItemPrice,
            filteredMenuImage,
            filteredMenuOwnerName,
            requireContext()
        )
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter=adapter

        setupSearchView()

        showAllMenu()

        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()
        filteredMenuOwnerName.clear()

        filteredMenuFoodName.addAll(originalMenuFoodName)
        filteredMenuItemPrice.addAll(originalMenuItemPrice)
        filteredMenuImage.addAll(originalMenuImage)
        filteredMenuOwnerName.addAll(originalMenuOwnerName)

        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMenuItems(newText.orEmpty())
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            private fun filterMenuItems(query: String) {
                if (query.isBlank()) {
                    showAllMenu()
                    return
                }

                filteredMenuFoodName.clear()
                filteredMenuItemPrice.clear()
                filteredMenuImage.clear()
                filteredMenuOwnerName.clear()

                originalMenuFoodName.forEachIndexed { index, foodName ->
                    val ownerName = originalMenuOwnerName[index]

                    // 🌟 FIX: Use an OR (||) condition to check both Food Name and Owner Name
                    val matchesFood = foodName.contains(query, ignoreCase = true)
                    val matchesOwner = ownerName.contains(query, ignoreCase = true)

                    if (matchesFood || matchesOwner) {
                        filteredMenuFoodName.add(foodName)
                        filteredMenuItemPrice.add(originalMenuItemPrice[index])
                        filteredMenuImage.add(originalMenuImage[index])
                        filteredMenuOwnerName.add(ownerName)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
    }
}