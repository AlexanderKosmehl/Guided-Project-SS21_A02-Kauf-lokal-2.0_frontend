
package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnStart
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Address
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.User
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.VotingOption
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*


/**
 * [RecyclerView.Adapter] that can display all [VotingOption]s.
 */

// TODO: fix the warning later
class PollRecyclerViewAdapter(
    private var options: List<VotingOption>,
    private var totalAmountVoter: Int,
    private var pollId: UUID
) : RecyclerView.Adapter<PollRecyclerViewAdapter.ViewHolder>() {
    var isClicked = false
    var dummyUser: User? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_poll_option_item, parent, false)

        // create dummyUser
        var url = "http://10.0.2.2:8080/user/dummy"
        val gson = Gson()
        val context = view.context

        val request = JsonObjectRequest(
            Request.Method.GET, url , null,
            { response ->

                dummyUser = gson.fromJson(response.toString(), User::class.java)
                //Toast.makeText(context, "User is ${dummyUser?.firstName}",Toast.LENGTH_SHORT).show()

            },
            { error ->
                Toast.makeText(context, "No DummyUser found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Kein DummyUser vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)


        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.optionName.text = option.title
        val context = holder.context

        // Seekbar-Hack: Overwrite OnToucListener to prevent UserInput
        holder.optionPercentage.setOnTouchListener(OnTouchListener { v, event -> true })



        // TODO: if dummyUser already voted: clicked = true
        var url = "http://10.0.2.2:8080/userVoted/${option.id}/${dummyUser?.id}"
        var userVoted:Boolean = false
        var request = JsonObjectRequest(
            Request.Method.GET, url , null,
            { response ->

                userVoted = response.toString().toBoolean()
                if (!userVoted) {
                    // TODO: toast: you already voted
                    Toast.makeText(context, "You already voted!", Toast.LENGTH_SHORT).show()
                }

            },
            { error ->
                Log.e("Response", error.message ?: "Kein Voting vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)

        if (userVoted) {
            isClicked = true
        }



        // check whether one item was already clicked
        if (isClicked) {
            isClicked = true

            // calculate percentage
            var percentage = getPercentage(option.totalAmountVoters, totalAmountVoter)

            ObjectAnimator.ofFloat(holder.optionName, "x", 46f).apply{
                duration = 500
                doOnStart {
                    //pass percentage to seekbar
                    ObjectAnimator.ofInt(holder.optionPercentage, "progress", percentage).apply {

                        duration = 1000
                        holder.optionPercentage.visibility = VISIBLE
                        // custom cubic-belzier curve, created with: https://cubic-bezier.com/
                        val custInterpolator: Interpolator = PathInterpolatorCompat.create(.42f,0f,.58f,1f)
                        interpolator = custInterpolator
                    }.start()
                }
            }.start()

        }


    }

    override fun getItemCount(): Int = options.size

    // Automatically displays data changes
    fun setValues(options: List<VotingOption>, totalAmountVoter: Int, pollId: UUID) {
        // sorts events by Date create
        //this.events = events.sortedByDescending { it.created }
        this.options = options
        this.totalAmountVoter = totalAmountVoter
        this.pollId = pollId
        this.notifyDataSetChanged()

    }

    fun getPercentage(part: Int, total: Int): Int {
        return (part.toDouble() / total * 100).toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        var optionName: TextView = view.findViewById(R.id.poll_option_name)
        var optionPercentage: SeekBar = view.findViewById(R.id.percentage_box)
        var card: MaterialCardView = view.findViewById(R.id.poll_option_card)

        init {

            itemView.setOnClickListener {
                var position: Int = bindingAdapterPosition
                val option = options[position]


                // actions clicked = true

                isClicked = true
                card.isFocused
                card.cardElevation = 0F
                card.strokeWidth = 1
                //card.strokeColor = strokeColor(getResources().getColor(R.color.GRAY))

                // necessary to influence other items
                notifyDataSetChanged()
                postOption(option.id, pollId, context)



            }
        }


    }

    fun postOption(optionId: UUID, pollId: UUID, context: Context) {

        val gson = Gson()
        ///poll/{voteId}/vote/{voteOptionId}
        val url = "http://10.0.2.2:8080/poll/${pollId}/vote/${optionId}"

        val mockBody = JSONObject(Gson().toJson(dummyUser))

        val request = JsonObjectRequest(
            Request.Method.POST, url, mockBody,
            { response ->
                //Toast.makeText(context, "POST Successful: $response", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Log.e("Response", error.message ?: "Kein POST möglich")
            }
        )

        RequestSingleton.getInstance(context).addToRequestQueue(request)

    }

}