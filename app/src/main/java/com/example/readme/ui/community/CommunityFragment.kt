package com.example.readme.ui.community

import com.example.readme.R
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class CommunityFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_community) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowCommunity()
    }
}