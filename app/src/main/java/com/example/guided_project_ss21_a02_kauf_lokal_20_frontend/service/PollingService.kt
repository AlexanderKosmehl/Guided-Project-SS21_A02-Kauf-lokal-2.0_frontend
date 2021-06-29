package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.readValues

class PollingService(val context: Context) {
    private val mapper = jacksonObjectMapper()

    fun pollCoupons(): List<Coupon> {
        val url = "http://10.0.2.2:8080/coupon"
        var coupons: List<Coupon> = listOf()

        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val body = response.toString()
                coupons = mapper.readValue(body)
                Log.i("Coupons:", response.toString())
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "No content found",Toast.LENGTH_SHORT).show()
                Log.e("No Response", error.message ?: "NICHTS")
            }
        )

        // Access the RequestQueue through your singleton class.
        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)


        return coupons

    }

}