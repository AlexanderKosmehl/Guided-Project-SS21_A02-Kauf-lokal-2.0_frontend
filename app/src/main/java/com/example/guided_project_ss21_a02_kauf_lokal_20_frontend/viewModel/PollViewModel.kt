package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Poll
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.Constants
import com.google.gson.Gson

class PollViewModel(application: Application) : AndroidViewModel(application) {

    private val logTAG = "PollViewModel"

    private lateinit var event: Event
    private lateinit var vendorID: String

    private val pollLiveData: MutableLiveData<Poll> by lazy {
        MutableLiveData<Poll>().also {
            addPollVoting(event)
        }
    }

    private val vendorLiveData: MutableLiveData<Vendor> by lazy {
        MutableLiveData<Vendor>().also {
            addAuthor(vendorID)
        }
    }

    fun getPoll(event: Event): LiveData<Poll> {
        this.event = event
        return pollLiveData
    }

    private fun getAuthor(vendorID: String): LiveData<Vendor> {
        this.vendorID = vendorID
        return vendorLiveData
    }

    private fun addPollVoting(event: Event) {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, Constants.URL_POLL + event.refId, null,
            { response ->
                val poll = Gson().fromJson(response.toString(), Poll::class.java)
                getAuthor(poll.vendorId)
                pollLiveData.value = poll
            },
            { error ->
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        ))
    }

    private fun addAuthor(vendorId: String) {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, Constants.URL_VENDORS + "/" + vendorId, null,
            { response ->
                vendorLiveData.value = Gson().fromJson(response.toString(), Vendor::class.java)
            },
            { error ->
                Log.e("Response", error.message ?: "Kein Vendor vorhanden")
            }
        ))
    }


}