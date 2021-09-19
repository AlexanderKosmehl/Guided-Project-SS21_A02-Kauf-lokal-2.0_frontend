
package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnStart
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Poll
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.User
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.VotingOption
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.Constants
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*


/**
 * [RecyclerView.Adapter] that can display all [VotingOption]s.
 */
// TODO: fix the warning later
class PollRecyclerViewAdapter(private var poll: Poll) :
    RecyclerView.Adapter<PollRecyclerViewAdapter.ViewHolder>() {
    var isClicked = false
    var dummyUser: User? = null

    // Automatically displays data changes
    @SuppressLint("NotifyDataSetChanged")
    fun setValues(poll: Poll) {
        this.poll = poll
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_poll_option_item, parent, false)

        // create dummyUser
        RequestSingleton.getInstance(view.context).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, Constants.URL_DUMMY, null,
            { response ->
                dummyUser = Gson().fromJson(response.toString(), User::class.java)
                //Toast.makeText(context, "User is ${dummyUser?.firstName}",Toast.LENGTH_SHORT).show()
            },
            { error ->
                //Toast.makeText(context, "No DummyUser found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Kein DummyUser vorhanden")
            }
        ))
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = poll.votingOptions[position]
        holder.optionName.text = option.title

        // Seekbar-Hack: Overwrite OnToucListener to prevent UserInput
        holder.optionPercentage.setOnTouchListener { _, _ -> true }
        checkOption(holder, option)
    }

    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var optionName: TextView = view.findViewById(R.id.poll_option_name)
        var optionPercentage: SeekBar = view.findViewById(R.id.percentage_box)
        var card: MaterialCardView = view.findViewById(R.id.poll_option_card)

        init {
            itemView.setOnClickListener {
                val position: Int = bindingAdapterPosition
                val option = poll.votingOptions[position]
                // actions clicked = true

                isClicked = true
                card.isFocused
                card.cardElevation = 0F
                card.strokeWidth = 1
                //card.strokeColor = strokeColor(getResources().getColor(R.color.GRAY))

                // necessary to influence other items
                notifyDataSetChanged()
                postOption(option.id, poll.id, view.context)
            }
        }
    }

    fun postOption(optionId: UUID, pollId: UUID, context: Context) {
        RequestSingleton.getInstance(context).addToRequestQueue(JsonObjectRequest(
            Request.Method.POST,
            Constants.URL_POLL + "$pollId/vote/$optionId",
            JSONObject(Gson().toJson(dummyUser)),
            { response ->
                Toast.makeText(context, "Erfolgreich abgestimmt.", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Log.e("Response", error.message ?: "Kein POST möglich")
                Toast.makeText(context, "Sie haben bereits abgestimmt!", Toast.LENGTH_SHORT).show()
            }
        ))
    }

    //-------
    override fun getItemCount(): Int = poll.votingOptions.size
    private fun getPercentage(part: Int, total: Int): Int = (part.toDouble() / total * 100).toInt()
    private fun checkOption(holder: ViewHolder, option: VotingOption) {
        // check whether one item was already clicked
        if (isClicked) {
            isClicked = true

            // calculate percentage
            val percentage = getPercentage(option.totalAmountVoters, poll.totalAmountVoters)

            ObjectAnimator.ofFloat(holder.optionName, "x", 46f).apply {
                duration = 500
                doOnStart {
                    //pass percentage to seekbar
                    ObjectAnimator.ofInt(holder.optionPercentage, "progress", percentage).apply {

                        duration = 1000
                        holder.optionPercentage.visibility = VISIBLE
                        // custom cubic-belzier curve, created with: https://cubic-bezier.com/
                        interpolator = PathInterpolatorCompat.create(.42f, 0f, .58f, 1f)
                    }.start()
                }
            }.start()
        }
    }
}


/*
    // Automatically displays data changes
    @SuppressLint("NotifyDataSetChanged")
    fun setValues(poll: Poll) {
        // sorts events by Date create
        //this.events = events.sortedByDescending { it.created }
        this.poll = poll
        this.notifyDataSetChanged()
    }
 */