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
class VendorListRecyclerViewAdapter(private var vendors: List<Vendor>) :
    RecyclerView.Adapter<VendorListRecyclerViewAdapter.ViewHolder>() {

    var stars: Float = 0.0F

    // Automatically displays data changes
    @SuppressLint("NotifyDataSetChanged")
    fun setValues(vendors: List<Vendor>) {
        this.vendors = vendors
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_vendor_list_item, parent, false)
        return ViewHolder(view)
    }

    /* TODO: - (59) remove name-fix when names in BE is fixed
             - (72) vendorScore is currently an integer
                    Remove fix once backend fixes vendorScores
             - (74) Remove fix once backend fixes null fields
             - (81) Calculate value once backend adds support
             - (84) Implement user management
     */

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val distanceText = (Random.nextInt(20) * 50).toString() + " m"

        val vendor = vendors[position]
        val vendorColorString = vendor.color.ifEmpty { "#16161d" }

        val vendorColor = Color.parseColor(vendorColorString)

        if (vendor.logo.isEmpty()) {
            holder.logoView.visibility = View.GONE
            if (vendor.name != "Forum Gummersbach")
                holder.titleView.text = vendor.name.replace("Gummersbach", "", false)
            else holder.titleView.text = vendor.name
        } else

            adjustTextColor(vendorColorString, holder)

        holder.headerLayout.setBackgroundColor(vendorColor)
        holder.ratingBar.rating =
            if (vendor.vendorScore.toFloat() == 0.0F) listOf(
                0.6F,
                5.0F,
                3.2F,
                2.8F,
                1.2F,
                2.3F
            ).random()
            else vendor.vendorScore.toFloat()

        stars = holder.ratingBar.rating
        holder.categoryView.text = vendor.category.name
        holder.isOpenView.text = if (vendor.openingTime.isOpen) "Geöffnet" else "Geschlossen"
        holder.isOpenView.setTextColor(
            holder.isOpenView.context.resources.getColor(
                if (vendor.openingTime.isOpen) R.color.open_color else R.color.close_color,
                null
            )
        )
        holder.distanceView.text = distanceText
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
        holder.categoryUnfoldView.text = vendor.category.name ?: "Keine Kategorie"
        holder.websiteUnfoldView.text = vendor.websiteURL
        holder.addressUnfoldView.text = "${vendor.address.street} ${vendor.address.houseNr}"
        holder.ratingCountUnfoldView.text =
            if (vendor.ratings.isEmpty()) "(${listOf(10, 20, 12, 55, 5, 11).random()})"
            else "(${vendor.ratings.size})"
        // TODO Remove fix once backend fixes vendorScores
        holder.ratingBarUnfold.rating =
            if (vendor.vendorScore.toFloat() == 0.0F) stars
            else vendor.vendorScore.toFloat() ?: listOf(0, 1, 2, 3, 4, 5).random().toFloat()

        holder.isOpenUnfoldView.text = if (vendor.openingTime.isOpen) "Geöffnet" else "Geschlossen"
        holder.isOpenUnfoldView.setTextColor(
            holder.isOpenUnfoldView.context.resources.getColor(
                if (vendor.openingTime.isOpen)
                    R.color.open_color else R.color.close_color, null
            )
        )

        holder.couponsButton.setBackgroundColor(vendorColor)
        holder.routeButton.setBackgroundColor(vendorColor)
        holder.websiteUnfoldImage.setColorFilter(vendorColor)
        holder.addressUnfoldImage.setColorFilter(vendorColor)
        holder.distanceUnfoldView.text = distance

        holder.routeButton.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=${vendor.name} ${vendor.address.zipCode} ${vendor.address.place} ${vendor.address.street} ${vendor.address.houseNr}")
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

