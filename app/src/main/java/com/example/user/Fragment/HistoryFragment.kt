package com.example.user.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user.R
import com.example.user.adapter.BuyAgainAdapter
import com.example.user.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val buyAgainFoodName = arrayListOf("Food 1", "Food 2", "Food 3")
        val buyAgainFoodOwner = arrayListOf("Owner A", "Owner B", "Owner C")  // New owner names
        val buyAgainFoodPrice = arrayListOf("$10", "$8", "$30")
        val buyAgainFoodImage = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)

        buyAgainAdapter = BuyAgainAdapter(
            buyAgainFoodName,
            buyAgainFoodOwner,    // Pass owner names
            buyAgainFoodPrice,
            buyAgainFoodImage
        )

        binding.buyAgainRecyclerView.adapter = buyAgainAdapter
        binding.buyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
