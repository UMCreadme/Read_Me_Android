package com.example.readme.ui.home.main

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.readme.R
import com.example.readme.databinding.FragmentHomeBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.home.Feed.FeedViewModel
import com.example.readme.ui.home.make.MakeFragment
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val feedViewModel: FeedViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowHome()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        feedViewModel.fetchFeeds()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        // categories 데이터가 변경될 때마다 UI를 업데이트
        feedViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            if (categories != null && categories.isNotEmpty()) {
                val viewPagerAdapter = ViewPagerAdapter(this, ArrayList(categories))
                binding.viewPager.adapter = viewPagerAdapter
                TabLayoutMediator(binding.tbCategory, binding.viewPager) { tab, position ->
                    tab.text = categories[position]
                }.attach()
            } else {
                Log.e("HomeFragment", "Categories list is null or empty")
            }
        })





        binding.btnMakeshorts.setOnClickListener {
            (activity as MainActivity).addFragment(MakeFragment())
        }
    }
}
