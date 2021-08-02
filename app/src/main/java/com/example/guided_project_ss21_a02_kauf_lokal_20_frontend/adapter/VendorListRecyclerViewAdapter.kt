package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
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
        val distanceText = (Random.nextInt(20) * 50).toString() + " m"

        val vendor = vendors[position]
        val vendorColorString = vendor.color.ifEmpty { "#16161d" }

        val vendorColor = Color.parseColor(vendorColorString)

        // TODO handle profilePicture URL(?) once backend implements it
        if (vendor.logo != null) {
            // Handle picture
        } else {
            holder.logoView.visibility = View.GONE
            if (vendor.name != "Forum Gummersbach") // TODO remove fix when names in BE is fixed
                holder.titleView.text = vendor.name.replace("Gummersbach", "", false)
            else {
                holder.titleView.text = vendor.name
            }
        }

        adjustTextColor(vendorColorString, holder)

        holder.headerLayout.setBackgroundColor(vendorColor)

        // TODO vendorScore is currently an integer
        // TODO Remove fix once backend fixes vendorScores
        holder.ratingBar.rating = vendor.vendorScore.toFloat()

        // TODO Remove fix once backend fixes null fields
        holder.categoryView.text = vendor.category.name
        holder.isOpenView.text = if (vendor.openingTime.isOpen == true) "Geöffnet" else "Geschlossen"
        holder.isOpenView.setTextColor(
            holder.isOpenView.context.resources.getColor(
                if (vendor.openingTime.isOpen) R.color.open_color else R.color.close_color,
                null
            )
        )

        // TODO Calculate value once backend adds support
        holder.distanceView.text = distanceText

        // TODO Implement user management
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
                displayHiddenItems(holder, vendor, vendorColor, distanceText)
                TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
            }
        }
    }

    private fun adjustTextColor(hexColor: String, holder: ViewHolder) {
        val brightnessColor = listOf(hexColor[3], hexColor[4], hexColor[5], hexColor[6])
        val brightnessArray = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        val brightnessHex = listOf('a', 'b', 'c', 'd', 'e', 'f')

        Log.i("adjust Text Color", hexColor)

        if (brightnessArray.contains(brightnessColor[0])
            && brightnessArray.contains(brightnessColor[1])
        ) {
            if (!brightnessHex.contains(brightnessColor[2])) {
                holder.titleView.setTextColor(
                    holder.titleView.context.resources.getColor(R.color.white, null)
                )
            }
        }
    }

    override fun getItemCount(): Int = vendors.size

    private fun displayHiddenItems(
        holder: ViewHolder,
        vendor: Vendor,
        vendorColor: Int,
        distance: String,
    ) {
        // TODO Remove fix once backend fixes null fields
        holder.categoryUnfoldView.text = vendor.category?.name ?: "Keine Kategorie"
        holder.websiteUnfoldView.text = vendor.websiteURL
        holder.addressUnfoldView.text = "${vendor.address.street} ${vendor.address.houseNr}"
        holder.ratingCountUnfoldView.text = "(${vendor.ratings.size})"
        // TODO Remove fix once backend fixes vendorScores
        holder.ratingBarUnfold.rating = vendor.vendorScore?.toFloat() ?: listOf(0,1,2,3,4,5).random().toFloat()

        holder.isOpenUnfoldView.text = if (vendor.openingTime?.isOpen == true) "Geöffnet" else "Geschlossen"
        holder.isOpenUnfoldView.setTextColor(
            holder.isOpenUnfoldView.context.resources.getColor(
                if (vendor.openingTime?.isOpen == true)
                    R.color.open_color else R.color.close_color, null
            )
        )

        holder.couponsButton.setBackgroundColor(vendorColor)
        holder.routeButton.setBackgroundColor(vendorColor)
        holder.websiteUnfoldImage.setColorFilter(vendorColor)
        holder.addressUnfoldImage.setColorFilter(vendorColor)
        holder.distanceUnfoldView.text = distance

        holder.routeButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${vendor.name} ${vendor.address.zipCode} ${vendor.address.place} ${vendor.address.street} ${vendor.address.houseNr}")
            val mapIntent: Intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            holder.routeButton.context.startActivity(mapIntent)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //--- both
        val headerLayout: ConstraintLayout = view.findViewById(R.id.couponHeaderLayout)
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

