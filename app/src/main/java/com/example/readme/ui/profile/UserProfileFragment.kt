package com.example.readme.ui.profile

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.readme.R
import com.example.readme.databinding.FragmentUserprofileBinding
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.utils.RetrofitClient
import com.google.android.material.tabs.TabLayoutMediator


class UserProfileFragment : BaseFragment<FragmentUserprofileBinding>(R.layout.fragment_userprofile) {

    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }
    private val viewModel: UserProfileViewModel by viewModels {
        UserProfileViewModelFactory(apiService)
    }

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowMyPage()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        val userId: Int = arguments?.getInt("userId") ?: 0
        var token = "example"

        // 프로필 정보 가져오기
        viewModel.fetchProfile(userId).observe(viewLifecycleOwner) { profileResponse ->
            if (profileResponse != null) {
                // 정상적인 응답 처리
                profileResponse?.let {
                    Log.d("UserProfileFragment", "Profile Response: $profileResponse")

                    // 프로필 정보를 UI에 업데이트
                    binding.profileName.text = it.result.nickname
                    binding.profileId.text = "@${it.result.account}"
                    binding.profileBio.text = it.result.comment ?: ""
                    binding.followersCount.text = "${it.result.followerNum}"
                    binding.followingCount.text = "${it.result.followingNum}"

                    // 이미지 로딩 Glide : 비동기로 처리
                    Glide.with(this)
                        .load(it.result.profileImg)
                        .transform(CircleCrop()) // CircleCrop으로 원형 자르기
                        .into(binding.profileImage)
                }
            } else {
                Log.e("UserProfileFragment", "Failed to get profile data")
            }
        }

        // ViewPager2와 TabLayoutMediator 설정
        val adapter = UserProfileViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0 -> { tab.setIcon(R.drawable.ic_tab_shorts) }
                1 -> { tab.setIcon(R.drawable.like_icon) }
                2 -> { tab.setIcon(R.drawable.post_detail_icon) }
            }
        }.attach()

        // 버튼 클릭 리스너 설정
        binding.btnPfFollow.setOnClickListener {
            binding.btnPfFollow.isSelected = binding.btnPfFollow.isSelected != true;

            val currentFollowingState = viewModel.isFollowing.value ?: false
            if (currentFollowingState) {
                viewModel.unfollowUser(token, userId)
            } else {
                viewModel.followUser(token, userId)
            }
        }

    }

}

