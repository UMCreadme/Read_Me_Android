package com.example.readme.ui.home

import com.example.readme.R
import com.example.readme.databinding.FragmentHomeBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowHome()
    }


}