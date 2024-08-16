package com.example.readme.ui.search

import android.view.View
import androidx.fragment.app.Fragment
import com.example.readme.R
import com.example.readme.databinding.FragmentHomeBinding
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.whashow.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowSearch()
    }
    

}
