package com.example.readme.ui.home.main

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.databinding.FragmentHomeBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.home.Feed.FeedViewModel
import com.example.readme.ui.home.make.MakeFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val feedViewModel: FeedViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowHome()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        // 워커 스레드에서 데이터 가져오기 - lifecycleScope를 사용하여 프래그먼트 생명주기에 맞춰 코루틴 관리
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                feedViewModel.fetchFeeds() // 네트워크 또는 시간이 오래 걸리는 작업
            } catch (e: Exception) {
                Log.e("HomeFragment", "Failed to fetch feeds", e)
            }
        }

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
