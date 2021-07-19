package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon

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
        // holder.textView.text = "Demo"
    }

    override fun getItemCount(): Int = coupons.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.percentageText)
    }


}
