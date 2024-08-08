package com.example.readme.ui.search

import android.os.Bundle
import android.view.View
import com.example.readme.R
import com.example.readme.databinding.FragmentSearchresultBinding
import com.example.whashow.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchResultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {

    private val tabs = arrayListOf("태그", "책", "프로필")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        // Bundle에서 keyword 값을 가져옴
        val keyword = arguments?.getString("keyword") ?: ""

        // ViewPager2 어댑터 설정
        val adapter = SearchResultAdapter(requireActivity(), keyword)
        binding.searchResultViewPager.adapter = adapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(binding.searchTab, binding.searchResultViewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}

