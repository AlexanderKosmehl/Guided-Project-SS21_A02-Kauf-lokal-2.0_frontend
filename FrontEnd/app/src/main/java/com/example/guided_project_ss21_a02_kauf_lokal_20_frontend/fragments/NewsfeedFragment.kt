package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.NewsfeedRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.EventTypes
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.TitleTexts
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel.NewsfeedListViewModel

/**
 * A fragment representing a list of Items for the newsfeed.
 */
class NewsfeedFragment : Fragment() {
    lateinit var events : List<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_newsfeed_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.newsfeedList)

        (activity as AppCompatActivity).supportActionBar?.title = TitleTexts.NEWSFEED

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsfeedRecyclerViewAdapter(listOf<Event>())
        }

        findEvents(recyclerView)
        return view
    }

    private fun findEvents(recyclerView: RecyclerView) {
        val model: NewsfeedListViewModel by viewModels()
        model.getEvents().observe(this, {
            (recyclerView.adapter as NewsfeedRecyclerViewAdapter).setValues(it)
            events = it
            findCoupon(recyclerView)
        })
    }

    private fun findCoupon(recyclerView: RecyclerView) {
        val couponEvents = events.filter { event -> event.eventTypes == EventTypes.COUPON }
        val adapter = recyclerView.adapter as NewsfeedRecyclerViewAdapter
        val model: NewsfeedListViewModel by viewModels()
        model.getCoupons(couponEvents.first().refId).observe(this, {
            adapter.coupon = it
        })
    }
}