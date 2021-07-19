package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.CouponListRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class CouponListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coupon_list, container, false) as RecyclerView

        // Set LayoutManager and Adapter
        with(view) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CouponListRecyclerViewAdapter(listOf<Coupon>())
        }

        addCouponsToAdapter(view)

        return view
    }

    // TODO connect to backend
    fun addCouponsToAdapter(view: RecyclerView) {
        val context = view.context
        val adapter = view.adapter as CouponListRecyclerViewAdapter

        adapter.setValues(listOf(
            Coupon(UUID(1, 2), "Name", "Description", 1, Date(), 12.0, Date()),
            Coupon(UUID(2, 3), "Name", "Description", 1, Date(), 12.0, Date()),
            Coupon(UUID(3, 4), "Name", "Description", 1, Date(), 12.0, Date()),
            Coupon(UUID(4, 5), "Name", "Description", 1, Date(), 12.0, Date())
        ))
    }
}