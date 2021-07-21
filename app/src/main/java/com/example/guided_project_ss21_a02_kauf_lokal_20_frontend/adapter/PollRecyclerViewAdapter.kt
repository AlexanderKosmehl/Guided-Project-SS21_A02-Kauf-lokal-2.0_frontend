package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.annotation.SuppressLint
import android.view.*
import android.view.View.OnTouchListener
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.VotingOption
import com.google.android.material.card.MaterialCardView


/**
 * [RecyclerView.Adapter] that can display all [VotingOption]s.
 */

// TODO: fix the warning later
class PollRecyclerViewAdapter(
    private var options: List<VotingOption>,
    private var totalAmountVoter: Int
) : RecyclerView.Adapter<PollRecyclerViewAdapter.ViewHolder>() {
    var isClicked = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_poll_option_item, parent, false)

        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.optionName.text = option.name

        //Seekbar-Hack: Overwrite OnToucListener to prevent UserInput
        holder.optionPercentage.setOnTouchListener(OnTouchListener { v, event -> true })



        // check whether one item was already clicked
        if (isClicked) {
            isClicked = true
            //holder.card.isFocused
            // move text to left TODO: FIX
            holder.optionName.gravity = Gravity.START

            // show percentage bar
            var percentage = getPercentage(option.amountVoters, totalAmountVoter)
            holder.optionPercentage.progress = percentage
            holder.optionPercentage.visibility = VISIBLE
            holder.optionName.visibility = VISIBLE

        }




    }

    override fun getItemCount(): Int = options.size



    // Automatically displays data changes
    fun setValues(options: List<VotingOption>, totalAmountVoter: Int) {
        // sorts events by Date create
        //this.events = events.sortedByDescending { it.created }
        this.options = options
        this.totalAmountVoter = totalAmountVoter
        this.notifyDataSetChanged()

    }

    fun getPercentage(part: Int, total: Int): Int {
        return (part.toDouble() / total * 100).toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var optionName: TextView = view.findViewById(R.id.poll_option_name)
        var optionPercentage: SeekBar = view.findViewById(R.id.percentage_box)
        var card: MaterialCardView = view.findViewById(R.id.poll_option_card)
        var checkMark: ImageView = view.findViewById(R.id.poll_checkmark)





        init {

            itemView.setOnClickListener {
                var position: Int = bindingAdapterPosition
                val option = options[position]
                val context = itemView.context





                // actions clicked = true

                isClicked = true
                card.isFocused
                card.cardElevation = 0F
                // move text to left TODO: FIX
                optionName.gravity = Gravity.START
                // show percentage bar
                var percentage = getPercentage(option.amountVoters, totalAmountVoter)
                optionPercentage.progress = percentage
                optionPercentage.visibility = VISIBLE
                checkMark.visibility = VISIBLE

                // necessary to influence other items
                notifyDataSetChanged()


            }
        }


    }

}