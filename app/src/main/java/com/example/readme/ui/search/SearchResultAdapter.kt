package com.example.readme.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.readme.ui.search.book.SearchBookFragment
import com.example.readme.ui.search.shorts.SearchShortsFragment
import com.example.readme.ui.search.user.SearchUserFragment

class SearchResultAdapter(
    fragmentActivity: FragmentActivity,
    private val keyword: String
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchShortsFragment().apply {
                arguments = Bundle().apply {
                    putString("keyword", keyword)
                }
            }
            1 -> SearchBookFragment().apply {
                arguments = Bundle().apply {
                    putString("keyword", keyword)
                }
            }
            2 -> SearchUserFragment().apply {
                arguments = Bundle().apply {
                    putString("keyword", keyword)
                }
            }
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}

