package com.example.user.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.user.MenuBottomSheetFragment
import com.example.user.R
import com.example.user.adapter.PopularAdapter
import com.example.user.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog= MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                // TODO(reason: "Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }

        })
        val foodName = listOf("Rice", "Lentil Soup", "momo", "item","Macaroni and Cheese", "Macaroni")
        val Price = listOf("500 TK", "700 TK", "800 TK", "100 TK","200 TK","500 TK")
        val popularFoodImages = listOf(R.drawable.menu1 ,R.drawable.menu2,R.drawable.menu3 , R.drawable.menu4 ,R.drawable.menu1 ,R.drawable.menu1)

        // --- FIX Applied Here ---
        // 1. Define the list for the owner/restaurant names
        val ownerName = listOf("Happy Tastes", "The Soup Co", "Momo Mania", "Restaurant X", "The Cheese Spot", "The Noodle Hut")

        // 2. Correctly pass all four lists to the PopularAdapter constructor
        val adapter= PopularAdapter(foodName, Price , popularFoodImages, ownerName)
        // -------------------------

        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }
}