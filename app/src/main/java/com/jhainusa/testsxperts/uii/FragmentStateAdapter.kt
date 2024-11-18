package com.jhainusa.testsxperts.uii

import android.app.Fragment
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity,private val subjectname : String ?) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3 // Number of tabs

    override fun createFragment(position: Int): androidx.fragment.app.Fragment {
        return when (position) {
            0 -> Overview()
            1 ->{
                val fragment = TestsGivenFragment()
                val bundle = Bundle()
                bundle.putString("key", subjectname)
                fragment.arguments = bundle
                fragment
            }
            2 -> DiscussionFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}
