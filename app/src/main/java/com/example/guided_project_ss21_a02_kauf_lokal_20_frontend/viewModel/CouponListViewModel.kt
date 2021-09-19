package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.Constants
import com.google.gson.Gson

class CouponListViewModel(application: Application) : AndroidViewModel(application) {

    private val logTAG = "CouponListViewModel"

    private val couponsLiveData: MutableLiveData<List<Coupon>> by lazy {
        MutableLiveData<List<Coupon>>().also {
            loadCoupons()
        }
    }

    fun getCoupons(): LiveData<List<Coupon>> = couponsLiveData

    private fun loadCoupons() {
        val coupons = mutableListOf<Coupon>()
        Volley.newRequestQueue(getApplication()).add(
            JsonArrayRequest(
                Request.Method.GET, Constants.URL_COUPONS, null,
                { response ->
                    for (i in 0 until response.length()) {
                        coupons.add(
                            Gson().fromJson(
                                response.getJSONObject(i).toString(),
                                Coupon::class.java
                            )
                        )
                    }
                    couponsLiveData.value = coupons
                },
                { error ->
                    Log.e(logTAG + "Response", error.message ?: "Keine Fehlermeldung vorhanden")
                }
            )
        )
    }
}