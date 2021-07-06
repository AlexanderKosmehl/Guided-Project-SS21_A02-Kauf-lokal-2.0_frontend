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

import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent.DummyItem
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments.DetailEvent
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */

// TODO fix the warning later
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

        //TODO: set all eventType
        when (event.javaClass.simpleName) {
            "Coupon" -> {
                holder.eventType.text = "Coupon"
                holder.eventMessage.text = "A new Coupon was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_tag)
            }
            else -> {holder.eventType.text = "unknown"}
        }


    }

    override fun getItemCount(): Int = events.size

    // Automatically displays data changes
    fun setValues(events: List<Event>) {
        this.events = events
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var eventMessage: TextView = view.findViewById(R.id.event_message)
        var eventType: TextView = view.findViewById(R.id.event_type)
        var eventIv :ImageView = view.findViewById(R.id.event_iv)


        init {

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, DetailEvent::class.java).apply {
                    putExtra("NUMBER", position)
                    /*putExtra("CODE", itemKode.text)
                    putExtra("CATEGORY", itemKategori.text)
                    putExtra("CONTENT", itemIsi.text)*/
                }
                context.startActivity(intent)
            }
        }

    }

}