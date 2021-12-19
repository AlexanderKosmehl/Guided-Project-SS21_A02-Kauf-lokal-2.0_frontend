package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter.TabVendorListMapAdapter
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.TitleTexts
import com.google.android.material.tabs.TabLayout

class VendorTabFragment : Fragment() {

    private lateinit var tabVendorListMapAdapter: TabVendorListMapAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewTab = inflater.inflate(R.layout.fragment_vendor_tab, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = TitleTexts.VENDOR_TAB

        viewPager = viewTab.findViewById<ViewPager>(R.id.viewpager)
        tabLayout = viewTab.findViewById<TabLayout>(R.id.tabLayout)
        addFragment(viewTab)

        return viewTab
    }

    private fun addFragment(view: View) {

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        tabVendorListMapAdapter = TabVendorListMapAdapter(childFragmentManager)
        tabVendorListMapAdapter.addFragment(VendorListFragment(), getString(R.string.tab_list))
        tabVendorListMapAdapter.addFragment(VendorMapFragment(), getString(R.string.tab_map))
        viewPager.adapter = tabVendorListMapAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}