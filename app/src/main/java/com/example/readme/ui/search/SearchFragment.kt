package com.example.readme.ui.search

import com.example.readme.R
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowSearch()
    }
}
