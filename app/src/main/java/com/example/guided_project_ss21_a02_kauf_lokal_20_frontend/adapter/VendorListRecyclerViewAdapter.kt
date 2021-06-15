package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R

import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent.DummyItem
import kotlin.random.Random

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class VendorListRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<VendorListRecyclerViewAdapter.ViewHolder>() {

    val colors = listOf(R.color.teal_200, R.color.teal_700, R.color.purple_200, R.color.purple_500, R.color.purple_700, R.color.star_fill)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_vendor_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.titleView.setBackgroundResource(colors.random())
        holder.ratingBar.rating = item.rating.toFloat()
        holder.categoryView.text = item.vendorCategory
        holder.isOpenView.text = if (item.isOpen) "Geöffnet" else "Geschlossen"
        holder.distanceView.text = item.distance.toString() + "m"
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.vendor_list_title)
        val ratingBar: RatingBar = view.findViewById(R.id.vendor_rating_bar)
        val categoryView: TextView = view.findViewById(R.id.vendor_category)
        val isOpenView: TextView = view.findViewById(R.id.vendor_is_open)
        val distanceView: TextView = view.findViewById(R.id.vendor_distance)
    }
}