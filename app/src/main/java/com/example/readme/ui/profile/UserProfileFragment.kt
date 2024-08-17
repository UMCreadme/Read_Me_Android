package com.example.readme.ui.profile

import MyPageViewModelFactory
import androidx.fragment.app.viewModels
import com.example.readme.R
import com.example.readme.databinding.FragmentUserprofileBinding
import androidx.fragment.app.viewModels
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.MainActivity
import com.example.readme.ui.mypage.ViewPagerAdapter
import com.example.readme.utils.RetrofitClient
import com.example.whashow.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class UserProfileFragment : BaseFragment<FragmentUserprofileBinding>(R.layout.fragment_userprofile) {

    private val information = arrayListOf("내 쇼츠", "찜 쇼츠", "읽은 책")

//    private val userId: String = "userId" // 실제 토큰으로 대체
//    private val apiService: ReadmeServerService by lazy {
//        RetrofitClient.apiService
//    }
//    private val viewModel: UserProfileViewModel by viewModels {
//        UserProfileViewModelFactory(userId, apiService)
//    }
//
//    override fun initStartView() {
//        super.initStartView()
//        (activity as MainActivity).ShowMyPage()
//    }
//
//    override fun initDataBinding() {
//        super.initDataBinding()
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = viewLifecycleOwner
//    }
//
//    override fun initAfterBinding() {
//        super.initAfterBinding()
//
//        // ViewPager2와 TabLayoutMediator 설정
//        val adapter = ViewPagerAdapter(this)
//        binding.viewPager.adapter = adapter
//
//        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
//            when(position){
//                0 -> { tab.setIcon(R.drawable.ic_tab_shorts) }
//                1 -> { tab.setIcon(R.drawable.ic_tab_likes) }
//                2 -> { tab.setIcon(R.drawable.ic_tab_books) }
//            }
//        }.attach()
//
//        // 프로필 정보 가져오기
//        viewModel.getProfile(userId).observe(viewLifecycleOwner) { profileResponse ->
//            // 프로필 정보를 UI에 업데이트하기
//        }
//
//    }

}