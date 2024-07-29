package com.example.readme.ui.mypage

import com.example.readme.R
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.whashow.base.BaseFragment

class MyPageFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_mypage) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowMyPage()
    }
}