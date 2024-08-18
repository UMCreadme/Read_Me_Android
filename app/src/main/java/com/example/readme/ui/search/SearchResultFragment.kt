package com.example.readme.ui.search

import com.example.readme.R
import com.example.readme.databinding.FragmentSearchresultBinding
import com.example.readme.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchResultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {

    private val tabs = arrayListOf("프레", "책", "프로필")

    override fun initStartView() {
        super.initStartView()

        val parentFragment = parentFragment
        if (parentFragment is SearchFragment) {
            parentFragment.hideKeyboard()
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
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

