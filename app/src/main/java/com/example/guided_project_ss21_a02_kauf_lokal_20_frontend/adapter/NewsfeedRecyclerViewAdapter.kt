package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R

import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments.DetailEvent
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.EventTypes
import java.time.Duration
import java.time.LocalDateTime

/**
 * [RecyclerView.Adapter] that can display a [Event].
 */

// TODO: fix the warning later
@SuppressLint("SetTextI18n")
class NewsfeedRecyclerViewAdapter(
    private var events: List<Event>
) : RecyclerView.Adapter<NewsfeedRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_newsfeed_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]



        val timePassed = getTimePassed(event.created)

        //TODO: set all eventType
        when (event.eventTypes) {
            EventTypes.COUPON -> {
                holder.eventType.text = "Coupon"
                holder.eventMessage.text = "A new Coupon was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_tag)
                holder.eventTime.text = timePassed
            }
            EventTypes.MESSAGE -> {
                holder.eventType.text = "Message"
                holder.eventMessage.text = "A new Message was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_baseline_article_24)
                holder.eventTime.text = timePassed
            }
            EventTypes.POLL -> {
                holder.eventType.text = "Poll"
                holder.eventMessage.text = "A new Poll was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_baseline_poll_24)
                holder.eventTime.text = timePassed
            }
            EventTypes.UPDATE -> {
                holder.eventType.text = "Update"
                holder.eventMessage.text = "A new Update was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_baseline_update_24)
                holder.eventTime.text = timePassed
            }
            else -> {holder.eventType.text = "unknown"}
        }


    }

    override fun getItemCount(): Int = events.size

    // Automatically displays data changes
    fun setValues(events: List<Event>) {
        // sorts events by Date create
        this.events = events.sortedByDescending { it.created }
        this.notifyDataSetChanged()
    }

    fun getTimePassed(dateTime: String): String {

        val dateTimeNow = LocalDateTime.now()
        val dateTimeParsed = LocalDateTime.parse(dateTime)
        val diff = Duration.between(dateTimeParsed, dateTimeNow).seconds



        return when {
             diff in 60..3599 -> (diff / 60).toString() + " min"
             diff in 3600..86399 -> (diff / 3600).toString() + " h"
             diff in 86400..604799 -> (diff / 86400).toString() + " d"
             diff in 604800..2629799 -> (diff / 604800).toString() + " w"
             diff in 2629800.. 31535999-> (diff / 2629800).toString() + " m"
             diff >= 31536000 -> (diff / 31536000).toString() + " y"

             else -> "$diff s"
         }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var eventMessage: TextView = view.findViewById(R.id.event_message)
        var eventType: TextView = view.findViewById(R.id.event_type)
        var eventIv :ImageView = view.findViewById(R.id.event_iv)
        var eventTime : TextView = view.findViewById(R.id.event_time)


        init {

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, DetailEvent::class.java).apply {
                    putExtra("NUMBER", position)
                    /*putExtra("CODE", eventKode.text)
                    putExtra("CATEGORY", eventKategori.text)
                    putExtra("CONTENT", eventIsi.text)*/
                }
                context.startActivity(intent)
            }
        }

    }

}