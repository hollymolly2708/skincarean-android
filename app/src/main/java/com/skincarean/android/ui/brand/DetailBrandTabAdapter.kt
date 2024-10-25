package com.skincarean.android.ui.brand

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailBrandTabAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = ProductByBrandFragment()
            1 -> fragment = ArticleBrandFragment()
            2 -> fragment = VideoBrandFragment()
            3 -> fragment = AboutBrandFragment()
        }
        return fragment as Fragment
    }
}