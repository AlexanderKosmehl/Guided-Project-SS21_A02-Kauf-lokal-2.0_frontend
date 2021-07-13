package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.NewsfeedRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.PollingService
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.google.gson.Gson

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
            adapter = NewsfeedRecyclerViewAdapter(listOf<Event>())
        }

        // Handles backend communication

        //val pollingService = PollingService(adapter)

        addEventsToAdapter(recyclerView)

        return view
    }

    fun addEventsToAdapter(view: RecyclerView) {
        val context = view.context
        val adapter = view.adapter as NewsfeedRecyclerViewAdapter
        val events = mutableListOf<Event>()
        val gson = Gson()

        //TODO: changed Port for temporary testing
        val url = "http://10.0.2.2:3000/event"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // JSONArray does not support iterable which means this has to be a regular for loop
                for (i in 0 until response.length()) {
                    val event = gson.fromJson(response.getJSONObject(i).toString(), Event::class.java)
                    events.add(event)
                }

                adapter.setValues(events)
            },
            { error ->
                // TODO Add meaningful error handling
                Toast.makeText(context, "No content found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)
    }
}