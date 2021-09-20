package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.CouponListRecyclerViewAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.TitleTexts
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel.CouponListViewModel

class CouponListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_coupon_list, container, false) as RecyclerView

        (activity as AppCompatActivity).supportActionBar?.title = TitleTexts.COUPON_LIST

        with(view) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CouponListRecyclerViewAdapter(listOf<Coupon>())
        }

        addCouponsToAdapterVM(view)

        return view
    }

    private fun addCouponsToAdapterVM(view: RecyclerView) {
        val model: CouponListViewModel by viewModels()
        model.getCoupons()
            .observe(this, {
                (view.adapter as CouponListRecyclerViewAdapter).setValues(it)
            })
    }
}