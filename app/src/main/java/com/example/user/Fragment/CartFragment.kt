package com.example.user.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user.PayOutActivity
import com.example.user.R
import com.example.user.adapter.CartAdapter
import com.example.user.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        // ---------- DATA LISTS ----------
        val cartFoodName = mutableListOf("Rice", "Lentil Soup", "momo", "Kabab","Macaroni and Cheese", "Macaroni")

        val cartOwnerName = mutableListOf("Happy Tastes", "The Soup Co", "Momo Mania", "Restaurant X", "The Cheese Spot", "The Noodle Hut")


        val cartItemPrice = mutableListOf("150 TK", "50 TK", "200 TK", "100 TK","300 TK","200 TK")

        val cartImage = mutableListOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu2,
            R.drawable.menu3
        )

        // ---------- SET ADAPTER ----------
        val adapter = CartAdapter(
            cartFoodName,
            cartOwnerName,
            cartItemPrice,
            cartImage
        )

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        // ---------- PROCEED BUTTON ----------
        binding.proceedButton.setOnClickListener {
            startActivity(Intent(requireContext(), PayOutActivity::class.java))
        }

        return binding.root
    }
}
