package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Poll
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.URIS
import com.google.gson.Gson

class PollViewModel(application: Application) : AndroidViewModel(application) {

    private val logTAG = "PollViewModel"

    private lateinit var event: Event
    private lateinit var vendorID: String

    private val pollLiveData: MutableLiveData<Poll> by lazy {
        MutableLiveData<Poll>().also {
            loadPollVoting(event)
        }
    }

    private val vendorLiveData: MutableLiveData<Vendor> by lazy {
        MutableLiveData<Vendor>().also {
            loadVendor()
        }
    }

/*    private val dummyUserLiveData: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadDummy()
        }
    }
*/
    fun getPoll(event: Event): LiveData<Poll> {
        this.event = event
        return pollLiveData
    }

    fun getVendor(): LiveData<Vendor> = vendorLiveData

//    fun getDummy(): LiveData<User> = dummyUserLiveData

    private fun loadPollVoting(event: Event) {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, URIS.POLL + "/" + event.refId, null,
            { response ->
                val poll = Gson().fromJson(response.toString(), Poll::class.java)
                vendorID = poll.vendorId
                pollLiveData.value = poll
            },
            { error ->
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        ))
    }

    private fun loadVendor() {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, URIS.VENDORS + "/" + vendorID, null,
            { response ->
                vendorLiveData.value = Gson().fromJson(response.toString(), Vendor::class.java)
            },
            { error ->
                Log.e("Response", error.message ?: "Kein Vendor vorhanden")
            }
        ))
    }
/*
    private fun loadDummy() {
        // create dummyUser
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, URIS.DUMMY, null,
            { response ->
                dummyUserLiveData.value = Gson().fromJson(response.toString(), User::class.java)
                //Toast.makeText(context, "User is ${dummyUser?.firstName}",Toast.LENGTH_SHORT).show()
            },
            { error ->
                //Toast.makeText(context, "No DummyUser found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Kein DummyUser vorhanden")
            }
        ))
    }

    fun updatePollOption(optionId: String, pollId: String, dummyUser : User) {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.POST,
            "${URIS.POLL}/$pollId/vote/$optionId",
            JSONObject(Gson().toJson(dummyUser)),
            { response ->
                Toast.makeText(getApplication(), "Erfolgreich abgestimmt.", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Log.e("Response", error.message ?: "Kein POST möglich")
                Toast.makeText(getApplication(), "Sie haben bereits abgestimmt!", Toast.LENGTH_SHORT).show()
            }
        ))
    }
*/
}