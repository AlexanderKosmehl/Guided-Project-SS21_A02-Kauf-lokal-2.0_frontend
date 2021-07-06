package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.NewsfeedRecyclerViewAdapter
import com.google.gson.Gson

class PollingService(private val view: RecyclerView) {

    val url = "http://10.0.2.2:8080/"
    val gson = Gson()

    //val queue = Volley.newRequestQueue(context)

    fun pollEvents() {
        pollCoupons()
    }

    fun pollCoupons() {
        val context = view.context
        val adapter = view.adapter as NewsfeedRecyclerViewAdapter
        val coupons = mutableListOf<Coupon>()


        val request = JsonArrayRequest(Request.Method.GET, url+"coupon", null,
            { response ->
                // JSONArray does not support iterable which means this has to be a regular for loop
                for (i in 0 until response.length()) {
                    val coupon = gson.fromJson(response.getJSONObject(i).toString(), Coupon::class.java)
                    coupons.add(coupon)
                }
                // TODO Maybe add vendors one by one for smoother vendor view?

                adapter.setValues(coupons)
            },
            { error ->
                // TODO Add meaningful error handling
                Toast.makeText(context, "No content found",Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)

    }





}