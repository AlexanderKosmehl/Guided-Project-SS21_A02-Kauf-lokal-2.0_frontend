package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.URIS
import com.google.gson.Gson
import java.util.*

class NewsfeedListViewModel(application: Application) : AndroidViewModel(application) {

    private val logTAG = "NewsfeedListViewModel"

    @SuppressLint("StaticFieldLeak")
    private lateinit var eventID: UUID

    private val eventsLiveData: MutableLiveData<List<Event>> by lazy {
        MutableLiveData<List<Event>>().also {
            loadEvents()
        }
    }

    // TODO: Currently Duplicate with CouponListViewModel
    private val couponsLiveData: MutableLiveData<Coupon> by lazy {
        MutableLiveData<Coupon>().also {
            loadCoupon()
        }
    }

    fun getEvents(): LiveData<List<Event>> = eventsLiveData

    fun getCoupons(eventID: UUID): LiveData<Coupon> {
        this.eventID = eventID
        return couponsLiveData
    }

    private fun loadEvents() {
        val events = mutableListOf<Event>()
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonArrayRequest(
            Request.Method.GET, URIS.EVENTS, null,
            { response ->
                for (i in 0 until response.length()) {
                    events.add(
                        Gson().fromJson(
                            response.getJSONObject(i).toString(),
                            Event::class.java
                        )
                    )
                }
                eventsLiveData.value = events
            },
            { error ->
                // TODO Add meaningful error handling
                Log.e(logTAG + "Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        ))
    }

    private fun loadCoupon() {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, URIS.COUPONS + "/" + eventID, null,
            { response ->
                couponsLiveData.value = Gson().fromJson(response.toString(), Coupon::class.java)
            },
            { error ->
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        ))
    }
}