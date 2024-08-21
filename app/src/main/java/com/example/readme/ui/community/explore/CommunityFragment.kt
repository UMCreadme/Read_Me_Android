package com.example.readme.ui.community.explore

import com.example.readme.R
import com.example.readme.databinding.FragmentCommunityBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {

    private val tabs = arrayListOf("탐색하기", "개설하기", "나의 채팅방")

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowCommunity()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        binding.lifecycleOwner = viewLifecycleOwner

        // ViewPager2 어댑터 설정
        val adapter = CommunityFragmentAdapter(requireActivity())
        binding.communityViewPager.adapter = adapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(binding.communityTab, binding.communityViewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}