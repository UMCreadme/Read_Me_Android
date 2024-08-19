package com.example.readme.ui.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    //Tab Fragment 이름 BackendAPI와 통일
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyShortsFragment()
            1 -> MyLikesFragment()
            2 -> MyBooksFragment()
            else -> throw IllegalStateException("잘못된 포지션: $position")
        }
    }

}
