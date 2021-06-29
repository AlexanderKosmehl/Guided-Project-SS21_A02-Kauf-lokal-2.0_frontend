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
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.VendorListRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 */
class VendorListFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vendor_list, container, false) as RecyclerView

        // Set LayoutManager and Adapter
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = VendorListRecyclerViewAdapter(DummyContent.ITEMS)
        }

        return view
    }
}