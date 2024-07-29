package com.example.readme.ui.home

import androidx.fragment.app.Fragment
import com.example.readme.R
import com.example.readme.databinding.FragmentHomeBinding
import com.example.readme.ui.MainActivity
import com.example.whashow.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowHome()
    }


}