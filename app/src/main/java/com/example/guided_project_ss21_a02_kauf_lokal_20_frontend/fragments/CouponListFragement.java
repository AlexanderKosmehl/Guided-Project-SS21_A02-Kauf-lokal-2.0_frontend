
package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

        import android.os.Bundle
        import android.transition.AutoTransition
        import android.transition.TransitionManager
        import android.util.Log
        import androidx.fragment.app.Fragment
        import androidx.recyclerview.widget.GridLayoutManager
        import androidx.recyclerview.widget.LinearLayoutManager
        import androidx.recyclerview.widget.RecyclerView
        import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
        import androidx.cardview.widget.CardView
        import androidx.constraintlayout.widget.ConstraintLayout
        import com.android.volley.Request
        import com.android.volley.Response
        import com.android.volley.toolbox.JsonArrayRequest
        import com.android.volley.toolbox.JsonObjectRequest
        import com.android.volley.toolbox.Volley
        import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.Model.Vendor
        import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.VendorListRecyclerViewAdapter
        import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
        import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent
        import com.google.gson.Gson

/**
 * A fragment representing a list of Items.
 */
class CouponListFragement : Fragment() {

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view = inflater.inflate(R.layout.fragment_vendor_list, container, false) as RecyclerView

        // Set LayoutManager and Adapter
        with(view) {
        layoutManager = LinearLayoutManager(context)
        adapter = (listOf<Coupon>())
        }

        // Handles backend communication
        addCouponstoAdapter(view)

        return view
        }

        fun addCouponstoAdapter(view: RecyclerView) {
        val context = view.context
        val adapter = view.adapter as CouponListRecycleViewAdapter

        val gson = Gson()

        val url = "http://10.0.2.2:8080/merchant"
        val queue = Volley.newRequestQueue(context)
        val coupons = mutableListOf<Coupon>()

        val request = JsonArrayRequest(Request.Method.GET, url, null,
        { response ->
        // JSONArray does not support iterable which means this has to be a regular for loop
        for (i in 0 until response.length()) {
        val coupon = gson.fromJson(response.getJSONObject(i).toString(), Coupon::class.java)
        vendors.add(coupon)
        }
        // TODO Maybe add vendors one by one for smoother vendor view?
        adapter.setValues(coupon)
        },
        { error ->
        // TODO Add meaningful error handling
        Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
        }
        )
        queue.add(request)
        }
        }