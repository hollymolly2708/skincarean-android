package com.skincarean.android.ui.order

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderTabLayoutAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = OrderPendingFragment()
            1 -> fragment = OrderCancelFragment()
            2 -> fragment = OrderCompletedFragment()
        }
        return fragment as Fragment
    }
}