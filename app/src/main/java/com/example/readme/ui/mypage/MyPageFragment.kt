package com.example.readme.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.readme.R
import com.example.readme.databinding.FragmentMypageBinding
import androidx.fragment.app.viewModels
import com.example.readme.ui.MainActivity
import com.example.whashow.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private var _binding: FragmentMypageBinding? = null
    private val information = arrayListOf("내 쇼츠", "찜 쇼츠", "읽은 책")

    private val viewModel: MyPageViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowMyPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set up the ViewPager with the sections adapter.
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Connect TabLayout and ViewPager2 with icons
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}