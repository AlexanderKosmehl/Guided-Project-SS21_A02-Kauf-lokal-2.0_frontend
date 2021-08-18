package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.PollRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Poll
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.google.gson.Gson
import java.util.*

class PollFragment(// TODO: Rename and change types of parameters

): Fragment() {
    private val args by navArgs<PollFragmentArgs>()
    //val event = args.event

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poll, container, false)
        val recyclerView = view.findViewById(R.id.fragment_poll_option_list) as RecyclerView
// Set LayoutManager and Adapter
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PollRecyclerViewAdapter(listOf(), 0, UUID(0L, 0L))
        }
        (activity as AppCompatActivity).supportActionBar?.title = "Umfragen"

        // Handles backend communication
        addPollVotingToAdapter(view)

        return view
    }

    fun addPollVotingToAdapter(view: View) {

        var event = args.event
        val context = view.context
        val gson = Gson()

        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_poll_option_list) as RecyclerView
        val adapter = recyclerView.adapter as PollRecyclerViewAdapter


        val votingImage: ImageView = view.findViewById(R.id.poll_iv)
        val votingTitle: TextView = view.findViewById(R.id.poll_title)
        val votingAuthorImage: ImageView = view.findViewById(R.id.poll_author_image)
        val votingAuthorName: TextView = view.findViewById(R.id.poll_author_name)
        val votingDate: TextView = view.findViewById(R.id.poll_date)

        val url = "http://10.0.2.2:8080/poll/"

        val request = JsonObjectRequest(
            Request.Method.GET, url + event.refId, null,
            { response ->

                val poll = gson.fromJson(response.toString(), Poll::class.java)

                //TODO: voting header image

                // get Author Data
                addAuthor(poll.vendorId, view)

                votingTitle.text = poll.title
                votingDate.text = event.formatDate()

                adapter.setValues(poll.votingOptions, poll.totalAmountVoters, poll.id)

            },
            { error ->
                // TODO Add meaningful error handling
                Toast.makeText(context, "No content found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)
    }

    fun addAuthor(vendorId:String, view: View) {
        val url = "http://10.0.2.2:8080/vendor/"
        val gson = Gson()
        val context = view.context
        val votingAuthorName: TextView = view.findViewById(R.id.poll_author_name)
        val votingAuthorImage: ImageView = view.findViewById(R.id.poll_author_image)

        val request = JsonObjectRequest(
            Request.Method.GET, url + vendorId, null,
            { response ->

                val vendor = gson.fromJson(response.toString(), Vendor::class.java)
                votingAuthorName.text = vendor.name

                // TODO: Author image
                Glide.with(this).load(vendor.logo).into(votingAuthorImage)


            },
            { error ->
                Toast.makeText(context, "No Vendor found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Kein Vendor vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)



    }

}