package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.VendorListRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.Constants
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel.VendorListViewModel

/**
 * A fragment representing a list of Items.
 */
class VendorListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_vendor_list, container, false) as RecyclerView

        (activity as AppCompatActivity).supportActionBar?.title = Constants.TITLE_VENDOR_LIST
        // Set LayoutManager and Adapter
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = VendorListRecyclerViewAdapter(listOf<Vendor>())
        }
        Log.i("VendorListFragment", "HELLO TEST")
        addVendorsToAdapterVM(view)
        return view
    }

    private fun addVendorsToAdapterVM(view: RecyclerView) {
        val model: VendorListViewModel by viewModels()
        model.getVendors()
            .observe(this, {
                (view.adapter as VendorListRecyclerViewAdapter).setValues(it)
            })
    }
}