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
        val view = inflater.inflate(R.layout.fragment_newsfeed_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.newsfeedList)
        // Set LayoutManager and Adapter
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsfeedRecyclerViewAdapter(listOf<Coupon>())
        }

        // Handles backend communication
        val pollingService = PollingService(recyclerView)
        pollingService.pollEvents()

        return view
    }
}