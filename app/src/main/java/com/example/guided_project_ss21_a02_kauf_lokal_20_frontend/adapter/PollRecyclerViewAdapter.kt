package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.VotingOption


/**
 * [RecyclerView.Adapter] that can display all [VotingOption]s.
 */

// TODO: fix the warning later
class PollRecyclerViewAdapter(
    private var options: List<VotingOption>
) : RecyclerView.Adapter<PollRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_poll_option_item, parent, false)

        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.optionName.text = option.name
        holder.optionPercentage.text = option.amountVoters.toString() + "%"
        Log.i("Binder", "Values set: ${options.size}")
        Log.i("Binder", "Values set: ${option.name}")


    }

    override fun getItemCount(): Int = options.size



    // Automatically displays data changes
    fun setValues(options: List<VotingOption>) {
        // sorts events by Date create
        //this.events = events.sortedByDescending { it.created }
        this.options = options
        this.notifyDataSetChanged()

    }

    fun getPercentage(): String {

        return TODO()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var optionName: TextView = view.findViewById(R.id.poll_option_name)
        var optionPercentage: TextView = view.findViewById(R.id.poll_option_percentage)



        init {

            itemView.setOnClickListener {
                if (it.isClickable) {
                    var position: Int = bindingAdapterPosition
                    val option = options[position]
                    val context = itemView.context

                    // TODO: move text to left
                    // TODO: show percentage
                    // TODO: visualize clicked = true


                    Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        }


    }

}