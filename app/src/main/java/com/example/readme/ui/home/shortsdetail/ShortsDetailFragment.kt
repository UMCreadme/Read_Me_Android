package com.example.readme.ui.home.shortsdetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.readme.R
import com.example.readme.databinding.FragmentShortsBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.data.entities.detail.ShortsDetailInfo
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.home.main.ShortsDetailAdapter

class ShortsDetailFragment : BaseFragment<FragmentShortsBinding>(R.layout.fragment_shorts) {

    private val viewModel: ShortsDetailViewModel by viewModels()
    private lateinit var shortsDetailAdapter: ShortsDetailAdapter

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val autoScrollInterval: Long = 5000
    private var isUserScrolling = false

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()

        val shortsId = arguments?.getInt("shortsId") ?: 0
        val start = arguments?.getString("start") ?: ""

        shortsDetailAdapter = ShortsDetailAdapter(ArrayList())
        binding.shortsViewPager.adapter = shortsDetailAdapter

        viewModel.fetchShortsDetails(shortsId, start="main", page = 1, size = 4)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        viewModel.shorts.observe(viewLifecycleOwner) { shortsDetails ->
            setInit(shortsDetails)
        }

        shortsDetailAdapter.setMyItemClickListener(object : ShortsDetailAdapter.MyItemClickListener {
            override fun onItemClick(shortsDetail: ShortsDetailInfo) {
                // 아이템 클릭 시의 동작
            }

            override fun onImageClick(shortsDetail: ShortsDetailInfo) {
                // 이미지 클릭 시의 동작
            }

            override fun onLikeClick(shortsDetail: ShortsDetailInfo, isLiked: Boolean) {
                viewModel.likeShorts(shortsDetail)
            }
        })
    }

    private fun setInit(shortsDetails: List<ShortsDetailInfo>) {
        shortsDetailAdapter.updateData(shortsDetails)

        setupAutoScroll()

        binding.shortsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        isUserScrolling = true
                        stopAutoScroll()
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (isUserScrolling) {
                            isUserScrolling = false
                        } else {
                            startAutoScroll()
                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val itemCount = binding.shortsViewPager.adapter?.itemCount ?: 0
                if (position == itemCount - 1) {
                    stopAutoScroll()
                }
            }
        })
    }

    private fun setupAutoScroll() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                val itemCount = binding.shortsViewPager.adapter?.itemCount ?: 0
                val currentItem = binding.shortsViewPager.currentItem
                val nextItem = if (currentItem < itemCount - 1) currentItem + 1 else itemCount - 1

                // 애니메이션이 완료된 후 페이지를 변경
                binding.shortsViewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, autoScrollInterval)
            }
        }

        // 자동 스크롤 시작
        startAutoScroll()
    }

    private fun startAutoScroll() {
        // 기존의 Runnable을 제거하고 새로 시작
        stopAutoScroll()
        handler.postDelayed(runnable, autoScrollInterval)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    override fun onResume() {
        super.onResume()
        if (!isUserScrolling) {
            startAutoScroll()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
    }
}
