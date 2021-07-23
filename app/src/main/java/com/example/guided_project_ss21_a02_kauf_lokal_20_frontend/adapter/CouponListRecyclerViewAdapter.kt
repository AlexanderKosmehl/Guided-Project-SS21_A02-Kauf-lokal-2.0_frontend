package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments.CouponListFragment
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments.CouponListFragmentDirections
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import java.time.LocalDate
import java.util.*

class CouponListRecyclerViewAdapter(
    private var coupons: List<Coupon>

) : RecyclerView.Adapter<CouponListRecyclerViewAdapter.ViewHolder>() {

    fun setValues(coupons: List<Coupon>) {
        this.coupons = coupons
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_coupon_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Coupon Color
        // TODO Use vendor color some something similar
        holder.couponHeader.setBackgroundResource(R.color.saturn_orange)

        // Coupon Logo
        // TODO Handle image

        // Coupon Text
        holder.couponText.text = coupons[position].description ?: "50 % Rabatt"

        // Coupon Date
        val createdDate = coupons[position].created
        val expiryDate = coupons[position].expiryDate

        val monthStrings = listOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August","September", "Oktober", "November", "Dezember")

        val calendar = GregorianCalendar()
        calendar.time = createdDate
        val createdString = "${calendar.get(Calendar.DAY_OF_MONTH)} ${monthStrings[calendar.get(Calendar.MONTH)]}"
        calendar.time = expiryDate
        val expiryString = "${calendar.get(Calendar.DAY_OF_MONTH)} ${monthStrings[calendar.get(Calendar.MONTH)]}"

        holder.couponDate.text = "$createdString - $expiryString" ?: "Ohne Ablaufdatum"

        // Click Listener
        holder.couponCardView.setOnClickListener {
            val action = CouponListFragmentDirections.actionCouponListToCouponDetailFragment(coupons[position])
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = coupons.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val couponCardView: CardView = view.findViewById(R.id.couponCardView)
        val couponHeader: ConstraintLayout = view.findViewById(R.id.couponHeaderLayout)
        val couponLogo: ImageView = view.findViewById(R.id.couponLogoView)
        val couponFavorite: ImageView = view.findViewById(R.id.couponIsFavo)
        val couponText: TextView = view.findViewById(R.id.couponText)
        val couponDate: TextView = view.findViewById(R.id.couponDate)
    }



}
