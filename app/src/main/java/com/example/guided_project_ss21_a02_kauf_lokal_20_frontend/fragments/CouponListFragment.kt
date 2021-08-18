package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.CouponListRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.google.gson.Gson

/**
 * A fragment representing a list of Items.
 */
class CouponListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coupon_list, container, false) as RecyclerView

        (activity as AppCompatActivity).supportActionBar?.title = "Coupons für dich"

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

        val gson = Gson()

        val url = "http://10.0.2.2:8080/coupon"
        val queue = Volley.newRequestQueue(context)
        val coupons = mutableListOf<Coupon>()

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val coupon = gson.fromJson(response.getJSONObject(i).toString(), Coupon::class.java)
                    coupons.add(coupon)
                }
                adapter.setValues(coupons)
            },
            { error ->
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        )
        queue.add(request)

        // Dummydata
//        adapter.setValues(listOf(
//            Coupon(UUID(1, 2), "Name", "Description", 1, Date(), 12.0, Date()),
//            Coupon(UUID(2, 3), "Name", "Description", 1, Date(), 12.0, Date()),
//            Coupon(UUID(3, 4), "Name", "Description", 1, Date(), 12.0, Date()),
//            Coupon(UUID(4, 5), "Name", "Description", 1, Date(), 12.0, Date())
//        ))
    }
}