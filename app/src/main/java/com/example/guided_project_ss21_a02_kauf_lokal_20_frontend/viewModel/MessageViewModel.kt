package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Message
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.URIS
import com.google.gson.Gson
import java.util.*

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val logTAG = "MessageViewModel"
    private lateinit var eventID: UUID
    private lateinit var vendorID: String

    private val messageLiveData: MutableLiveData<Message> by lazy {
        MutableLiveData<Message>().also {
            loadMessages()
        }
    }

    private val vendorLiveData: MutableLiveData<Vendor> by lazy {
        MutableLiveData<Vendor>().also {
            loadVendor()
        }
    }

    fun getMessage(eventID: UUID): LiveData<Message> {
        this.eventID = eventID
        Log.i(logTAG, "ID: $eventID")
        return messageLiveData
    }

    fun getVendor(): LiveData<Vendor> = vendorLiveData


    private fun loadMessages() {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, URIS.MESSAGE + eventID, null,
            { response ->
                // JSONArray does not support iterable which means this has to be a regular for loop
                val message = Gson().fromJson(response.toString(), Message::class.java)
                vendorID = message.vendorId
                messageLiveData.value = message
            },
            { error ->
                Toast.makeText(getApplication(), "No content found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        ))
    }

    private fun loadVendor() {
        RequestSingleton.getInstance(getApplication()).addToRequestQueue(JsonObjectRequest(
            Request.Method.GET, URIS.VENDORS +"/"+ vendorID, null,
            { response ->
                vendorLiveData.value = Gson().fromJson(response.toString(), Vendor::class.java)
            },
            { error ->
                Toast.makeText(getApplication(), "No Vendor found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Kein Vendor vorhanden")
            }
        ))
    }

}