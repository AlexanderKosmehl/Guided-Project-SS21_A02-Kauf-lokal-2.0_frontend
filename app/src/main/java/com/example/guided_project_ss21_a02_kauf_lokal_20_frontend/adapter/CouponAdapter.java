package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy.DummyContent.DummyItem
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class Coupon_Adapter extends FragmentPagerAdapter {

    public int numOfTabs;

    public PagerAdapter(FragmentManager ca){
        super(ca);
        this.numOfTabs = numOfTabs;
    }
    @Override

    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return new coupon_alle();
            case 1:
                return new coupon_favoriten();
            default:
                return 0;
        }
  }

    @Override
    public int getCount( return numOfTabs;)

        //      @Override  //Das muss in Main Activity?
    //        protected void onCreate(Bundle savedInstanceState){
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    //
    //        TabLayout tablayout = findViewById(R.id.tabBar);
    //        TabItem tabChats = findViewById(R.id.tabchats);
    //        TabItem tabStatus = findViewById(R.id.tabCalls);
    //        final ViewPager viewpager = findViewById(R.id.viewpager)
    //
    //        PagerAdapter pagerAdapter = new
    //                PagerAdapter(supportFragmentManager(),
    //                   tablayout.getTabCount());
    //    }
}