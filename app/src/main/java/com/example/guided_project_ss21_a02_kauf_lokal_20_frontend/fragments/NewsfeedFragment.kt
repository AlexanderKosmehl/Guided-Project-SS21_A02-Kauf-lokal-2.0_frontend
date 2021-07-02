package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.NewsfeedRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.PollingService

/**
 * A fragment representing a list of Items for the newsfeed.
 */
class NewsfeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vendor_list, container, false) as RecyclerView

        // Set LayoutManager and Adapter
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsfeedRecyclerViewAdapter(listOf<Coupon>())
        }

        // Handles backend communication
        val pollingService = PollingService(view)
        pollingService.pollEvents()

        return view
    }
}