package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.VotingOption
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay


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

            // calculate percentage
            var percentage = getPercentage(option.amountVoters, totalAmountVoter)

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
        val context = view.context
        var optionName: TextView = view.findViewById(R.id.poll_option_name)
        var optionPercentage: SeekBar = view.findViewById(R.id.percentage_box)
        var card: MaterialCardView = view.findViewById(R.id.poll_option_card)
        var cardConstraint: ConstraintLayout = view.findViewById(R.id.card_constraint_layout)


        init {

            itemView.setOnClickListener {
                var position: Int = bindingAdapterPosition
                val option = options[position]


                // actions clicked = true

                isClicked = true
                card.isFocused
                card.cardElevation = 0F




                // necessary to influence other items
                notifyDataSetChanged()


            }
        }


    }

}