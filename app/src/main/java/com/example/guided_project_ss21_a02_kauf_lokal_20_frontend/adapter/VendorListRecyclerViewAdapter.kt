package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.Model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent.DummyItem
import kotlin.random.Random

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class VendorListRecyclerViewAdapter(
    private var vendors: List<Vendor>

) : RecyclerView.Adapter<VendorListRecyclerViewAdapter.ViewHolder>() {

    private val colors = listOf(
        R.color.teal_200,
        R.color.teal_700,
        R.color.purple_200,
        R.color.purple_500,
        R.color.purple_700,
        R.color.star_fill
    )

    // Automatically displays data changes
    fun setValues(vendors: List<Vendor>) {
        this.vendors = vendors
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_vendor_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vendor = vendors[position]

        // TODO handle profilePicture URL(?) once backend implements it
        if (vendor.profilePicture != null) {
            // Handle picture
        } else {
            holder.titleView.text = vendor.name
        }

        // TODO Change when backend adds value
        holder.headerLayout.setBackgroundResource(colors.random())

        // TODO MerchantScore is currently an integer
        holder.ratingBar.rating = vendor.merchantScore.toFloat()

        // TODO Change when backend adds value
        holder.categoryView.text = "No Category"

        // TODO Calculate value once backend adds support
        holder.isOpenView.text = if (listOf(true, false).random()) "Geöffnet" else "Geschlossen"

        holder.isOpenView.setTextColor(
            holder.isOpenView.context.resources.getColor(
                if (holder.isOpenView.text == "Geöffnet") R.color.open_color else R.color.close_color,
                null
            )
        )

        // TODO Calculate value once backend adds support
        holder.distanceView.text = (Random.nextInt(20) * 50).toString() + "m"

        // TODO Change when backend adds value
        holder.isFavoView.setImageResource(
            if (listOf(true, false).random()) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )

        // Display vendor details on tap
        holder.cardView.setOnClickListener {
            if (holder.hiddenView.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
                holder.hiddenView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
                holder.hiddenView.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = vendors.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.vendor_title)
        val headerLayout: ConstraintLayout = view.findViewById(R.id.headerLayout)
        val ratingBar: RatingBar = view.findViewById(R.id.vendor_rating_bar)
        val categoryView: TextView = view.findViewById(R.id.vendor_category)
        val isOpenView: TextView = view.findViewById(R.id.vendor_is_open)
        val distanceView: TextView = view.findViewById(R.id.vendor_distance)
        val isFavoView: ImageView = view.findViewById(R.id.vendor_is_favo)
        val logoView: ImageView = view.findViewById(R.id.vendor_logo)

        val ratingBarViewHidden : RatingBar = view.findViewById(R.id.vendor_rating_bar2)


        val cardView: CardView = view.findViewById(R.id.vendorCardView)
        val hiddenView: ConstraintLayout = view.findViewById(R.id.hiddenView)
    }
}