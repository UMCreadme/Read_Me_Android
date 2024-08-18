package com.example.readme.ui.community.explore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CommunityFragmentAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CommunityExploreFragment()
//            1 -> CreateFragment()
//            2 -> MyChatFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}