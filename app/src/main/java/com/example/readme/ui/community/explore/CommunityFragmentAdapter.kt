package com.example.readme.ui.community.explore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.readme.ui.community.create.CommunityCreateStartFragment
import com.example.readme.ui.community.my.CommunityMyFragment

class CommunityFragmentAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CommunityExploreFragment()
            1 -> CommunityCreateStartFragment()
            2 -> CommunityMyFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}