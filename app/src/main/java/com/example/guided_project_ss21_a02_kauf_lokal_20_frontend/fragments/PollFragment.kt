package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.PollRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Poll
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.Constants
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel.PollViewModel
import java.util.*

class PollFragment : Fragment() {
    private val args by navArgs<PollFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poll, container, false)
        val recyclerView = view.findViewById(R.id.fragment_poll_option_list) as RecyclerView

        (activity as AppCompatActivity).supportActionBar?.title = Constants.TITLE_POLL

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PollRecyclerViewAdapter(
                Poll(
                    UUID(0L, 0L),
                    "",
                    "",
                    0,
                    listOf())
            )
        }

        // Handles backend communication
        addPollVotingToAdapterVM(recyclerView)

        return view
    }


    private fun addPollVotingToAdapterVM(recyclerView: RecyclerView) {
        val model: PollViewModel by viewModels()
        model.getPoll(args.event)
            .observe(this, {
                (recyclerView.adapter as PollRecyclerViewAdapter)
                    .setValues(it)
            })
    }

}