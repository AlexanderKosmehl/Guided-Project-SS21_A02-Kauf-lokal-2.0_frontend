package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.PollRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Poll
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.User
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.TitleTexts
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel.PollViewModel
import java.util.*

class PollFragment : Fragment() {
    private val args by navArgs<PollFragmentArgs>()
    lateinit var dummy: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poll, container, false)
        val recyclerView = view.findViewById(R.id.fragment_poll_option_list) as RecyclerView

        (activity as AppCompatActivity).supportActionBar?.title = TitleTexts.POLL

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PollRecyclerViewAdapter(
                Poll(UUID(0L, 0L), "", "", 0, listOf())
            )
        }

        // Handles backend communication
        findPoll(recyclerView, view)

        return view
    }

    // TODO: outsource
    private fun setPollStuff(view: View, poll: Poll) {
        val votingTitle: TextView = view.findViewById(R.id.poll_title)
        val votingDate: TextView = view.findViewById(R.id.poll_date)
        votingTitle.text = poll.title
        votingDate.text = "20.09.2021"
    }

    private fun findPoll(recyclerView: RecyclerView, view: View) {
        val model: PollViewModel by viewModels()
        model.getPoll(args.event)
            .observe(this, {
                setPollStuff(view, it)
                getAuthor(view)
                (recyclerView.adapter as PollRecyclerViewAdapter).setValues(it)
            })
    }

    // TODO: outsource
    private fun setVendorStuff(view: View, vendor: Vendor) {
        val votingAuthorName: TextView = view.findViewById(R.id.poll_author_name)
        val votingAuthorImage: ImageView = view.findViewById(R.id.poll_author_image)

        votingAuthorName.text = vendor.name
        Glide.with(this).load(vendor.logo).into(votingAuthorImage)
    }

    private fun getAuthor(view: View) {
        val model: PollViewModel by viewModels()
        model.getVendor()
            .observe(this, {
                setVendorStuff(view, it)
            })
    }
    /*
    private fun findDummyUser(view: View) {
        val model: PollViewModel by viewModels()
        model.getDummy().observe(this, {
         dummy = it
        })
    }


    private fun updatePollOptions(optionId: String, pollId: String) {

    }
*/
}