package com.example.readme.ui.community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    fun getPageTitle(position: Int): String = fragmentTitleList[position]
}
