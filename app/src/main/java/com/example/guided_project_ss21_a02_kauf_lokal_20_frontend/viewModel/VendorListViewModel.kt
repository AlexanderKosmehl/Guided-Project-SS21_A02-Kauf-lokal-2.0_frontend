package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.Constants
import com.google.gson.Gson

class VendorListViewModel(application: Application) : AndroidViewModel(application) {

    private val logTAG = "VendorListViewModel"

    private val vendorsLiveData: MutableLiveData<List<Vendor>> by lazy {
        MutableLiveData<List<Vendor>>().also {
            loadVendors()
        }
    }

    fun getVendors(): LiveData<List<Vendor>> = vendorsLiveData

    private fun loadVendors() {
        val vendors = mutableListOf<Vendor>()
        Volley.newRequestQueue(getApplication()).add(
            JsonArrayRequest(
                Request.Method.GET, Constants.URL_VENDORS, null,
                { response ->
                    for (i in 0 until response.length()) {
                        vendors.add(
                            Gson().fromJson(
                                response.getJSONObject(i).toString(),
                                Vendor::class.java
                            )
                        )
                    }
                    vendorsLiveData.value = vendors
                },
                { error ->
                    Log.e("$logTAG: Response", error.message ?: "Keine Fehlermeldung vorhanden")
                }
            )
        )
    }
}