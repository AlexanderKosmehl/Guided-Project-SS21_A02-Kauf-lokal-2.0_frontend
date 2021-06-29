package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

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
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsfeedRecyclerViewAdapter(
    private val coupons: List<Coupon>
) : RecyclerView.Adapter<NewsfeedRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_newsfeed_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coupon = coupons[position]
        holder.titleView.text = coupon.id
    }

    override fun getItemCount(): Int = coupons.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleView: TextView

        init {
            titleView = view.findViewById(R.id.event_title)

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