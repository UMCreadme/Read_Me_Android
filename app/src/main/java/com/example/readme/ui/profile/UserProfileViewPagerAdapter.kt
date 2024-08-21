package com.example.readme.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.readme.ui.mypage.MyBooksFragment
import com.example.readme.ui.mypage.MyLikesFragment

class UserProfileViewPagerAdapter(fragment: Fragment, private val userId: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserShortsFragment(userId)
            1 -> UserLikesFragment(userId)
            2 -> UserBooksFragment(userId)
            else -> throw IllegalStateException("잘못된 포지션: $position")
        }
    }

}
