package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class TabVendorListMapAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private final var fragmentArrayList : ArrayList<Fragment> = ArrayList()
    private final var fragmentTitle : ArrayList<String> = ArrayList()

    override fun getCount(): Int = fragmentArrayList.size

    override fun getItem(position: Int) : Fragment = fragmentArrayList[position]

    override fun getPageTitle(position: Int): CharSequence? = fragmentTitle[position]

    fun addFragment(fragment:Fragment, title: String) {
        fragmentArrayList.add(fragment)
        fragmentTitle.add(title)
    }

}