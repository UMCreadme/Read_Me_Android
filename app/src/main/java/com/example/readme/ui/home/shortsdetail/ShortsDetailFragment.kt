package com.example.readme.ui.home.shortsdetail

import android.view.View
import androidx.fragment.app.viewModels
import com.example.readme.R
import com.example.readme.databinding.FragmentShortsBinding
import com.example.readme.ui.MainActivity
import com.example.readme.data.entities.detail.ShortsDetailInfo
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.home.main.ShortsDetailAdapter

class ShortsDetailFragment : BaseFragment<FragmentShortsBinding>(R.layout.fragment_shorts) {

    // ViewModel 인스턴스 생성
    private val viewModel: ShortsDetailViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        // ViewModel을 통해 서버에서 ShortsDetail 데이터를 가져옴
        val shortsId = arguments?.getInt("shortsId") ?: 0
        val start = arguments?.getString("start") ?: ""
        // 서버 요청
        viewModel.fetchShortsDetails(shortsId, start="main", page = 1, size = 4)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        // 가져온 데이터를 옵저빙하고 UI를 업데이트
        viewModel.shorts.observe(viewLifecycleOwner) { shortsDetails ->
            setInit(shortsDetails)
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    // Adapter에 ShortsDetail 리스트를 설정
    private fun setInit(shortsDetails: List<ShortsDetailInfo>) {
        val adapter = ShortsDetailAdapter(shortsDetails)
        binding.shortsViewPager.adapter = adapter
    }
}
