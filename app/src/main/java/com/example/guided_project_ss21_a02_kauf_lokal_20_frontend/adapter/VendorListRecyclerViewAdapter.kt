package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.OpeningTime
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent.DummyItem
import kotlin.random.Random

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */

// TODO fix the warning later
@SuppressLint("SetTextI18n")
class VendorListRecyclerViewAdapter(
    private var vendors: List<Vendor>

) : RecyclerView.Adapter<VendorListRecyclerViewAdapter.ViewHolder>() {

    private val colors = listOf(
        R.color.risse_green,
        R.color.open_color,
        R.color.tchibo_blue,
        R.color.teal_700,
        R.color.teal_200,
        R.color.purple_200,
        R.color.purple_500,
        R.color.purple_700,
        R.color.star_fill,
        R.color.close_color,
        R.color.button_ripple_red,
        R.color.saturn_orange
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
        val color = colors.random()
        val isOpen = listOf(true, false).random()
        val distance = (Random.nextInt(20) * 50).toString() + "m"

        val vendor = vendors[position]

        // TODO handle profilePicture URL(?) once backend implements it
        // if (vendor.profilePicture != null) {
            // Handle picture
        // } else {
            holder.logoView.visibility = View.GONE
            if (vendor.name != "Forum Gummersbach") // TODO remove fix when names in BE is fixed
                holder.titleView.text = vendor.name.replace("Gummersbach", "", false)
            else holder.titleView.text = vendor.name
        // }

        // TODO Change when backend adds value
        holder.headerLayout.setBackgroundResource(color)

        // TODO MerchantScore is currently an integer
        holder.ratingBar.rating = vendor.merchantScore.toFloat()

        // TODO Change when backend adds value
        holder.categoryView.text = "Keine Kategorie"

        // TODO Calculate value once backend adds support
        holder.isOpenView.text = if (isOpen) "Geöffnet" else "Geschlossen"

        holder.isOpenView.setTextColor(
            holder.isOpenView.context.resources.getColor(
                if (holder.isOpenView.text == "Geöffnet") R.color.open_color else R.color.close_color,
                null
            )
        )

        // TODO Calculate value once backend adds support
        holder.distanceView.text = distance

        // TODO Change when backend adds value
        holder.isFavoView.setImageResource(
            if (listOf(true, false).random()) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )

        // Display vendor details on tap
        holder.cardView.setOnClickListener {
            if (holder.unfoldedView.visibility == View.VISIBLE) {
                holder.bodyLayout.visibility = View.VISIBLE
                holder.unfoldedView.visibility = View.GONE
            } else {
                holder.bodyLayout.visibility = View.GONE
                holder.unfoldedView.visibility = View.VISIBLE
                displayHiddenItems(holder, vendor, color, distance, isOpen)
                TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
            }
        }
    }

    // TODO something useful?
    private fun isVendorOpen(openingTime: OpeningTime): Boolean {
        return listOf(true, false).random()
    }

    override fun getItemCount(): Int = vendors.size

    // TODO Change when backend adds usable values
    private fun displayHiddenItems(
        holder: ViewHolder,
        vendor: Vendor,
        color: Int,
        distance: String,
        isOpen: Boolean
    ) {
        val colorRes = holder.couponsButton.context.resources.getColor(color, null)

        holder.categoryUnfoldView.text = "Keine Kategorie"
        holder.websiteUnfoldView.text = "Keine Website"
        holder.addressUnfoldView.text = "${vendor.address.street} ${vendor.address.houseNr}"
        holder.ratingCountUnfoldView.text = "(${Random.nextInt(1, 1001)})"
        holder.ratingBarUnfold.rating = vendor.merchantScore.toFloat()

        holder.isOpenUnfoldView.text = if (isOpen) "Geöffnet" else "Geschlossen"
        holder.isOpenUnfoldView.setTextColor(
            holder.isOpenUnfoldView.context.resources.getColor(
                if (holder.isOpenUnfoldView.text == "Geöffnet")
                    R.color.open_color else R.color.close_color, null
            )
        )

        holder.couponsButton.setBackgroundColor(colorRes)
        holder.routeButton.setBackgroundColor(colorRes)
        holder.websiteUnfoldImage.setColorFilter(colorRes)
        holder.addressUnfoldImage.setColorFilter(colorRes)
        holder.distanceUnfoldView.text = distance
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //--- both
        val headerLayout: ConstraintLayout = view.findViewById(R.id.headerLayout)
        val cardView: CardView = view.findViewById(R.id.vendorCardView)
        val titleView: TextView = view.findViewById(R.id.vendor_title)
        val isFavoView: ImageView = view.findViewById(R.id.vendor_is_favo)
        val logoView: ImageView = view.findViewById(R.id.vendor_logo)

        //--- collapsed
        val bodyLayout: ConstraintLayout = view.findViewById(R.id.simpleBodyLayout)
        val ratingBar: RatingBar = view.findViewById(R.id.vendor_rating_bar)
        val categoryView: TextView = view.findViewById(R.id.vendor_category)
        val isOpenView: TextView = view.findViewById(R.id.vendor_is_open)
        val distanceView: TextView = view.findViewById(R.id.vendor_distance)

        //--- unfolded
        val unfoldedView: ConstraintLayout = view.findViewById(R.id.unfoldedView)
        val couponsButton: Button = view.findViewById(R.id.vendor_coupons_button)
        val routeButton: Button = view.findViewById(R.id.vendor_route_button)
        val ratingBarUnfold: RatingBar = view.findViewById(R.id.vendor_rating_bar_unfold)
        val categoryUnfoldView: TextView = view.findViewById(R.id.vendor_category_unfold)
        val websiteUnfoldView: TextView = view.findViewById(R.id.vendor_website_link)
        val addressUnfoldView: TextView = view.findViewById(R.id.vendor_address_content)
        val isOpenUnfoldView: TextView = view.findViewById(R.id.vendor_is_open_unfold)
        val ratingCountUnfoldView: TextView = view.findViewById(R.id.vendor_rating_count)
        val distanceUnfoldView: TextView = view.findViewById(R.id.vendor_distance_unfold)
        val websiteUnfoldImage: ImageView = view.findViewById(R.id.vendor_web_icon)
        val addressUnfoldImage: ImageView = view.findViewById(R.id.vendor_address_icon)

    }
}

