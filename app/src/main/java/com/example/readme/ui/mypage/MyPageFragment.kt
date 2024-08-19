package com.example.readme.ui.mypage

import MyPageViewModelFactory
import android.content.Intent
import android.util.Log
import com.example.readme.R
import com.example.readme.databinding.FragmentMypageBinding
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.MainActivity
import com.example.readme.utils.RetrofitClient
import com.example.readme.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val information = arrayListOf("내 쇼츠", "찜 쇼츠", "읽은 책")

    private val token = "example_token_here" //실제 토큰 필요
    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }
    private val viewModel: MyPageViewModel by viewModels {
        MyPageViewModelFactory(token, apiService)
    }

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowMyPage()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 프로필 정보 가져오기
        viewModel.fetchMyPage().observe(viewLifecycleOwner) { MyPageResponse ->
            if (MyPageResponse != null) {
                // 정상적인 응답 처리
                MyPageResponse?.let {
                    Log.d("UserProfileFragment", "MyPage Response: $MyPageResponse")

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
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // ViewPager2와 TabLayoutMediator 설정
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0 -> { tab.setIcon(R.drawable.ic_tab_shorts) }
                1 -> { tab.setIcon(R.drawable.ic_tab_likes) }
                2 -> { tab.setIcon(R.drawable.ic_tab_books) }
            }
        }.attach()

        // 프로필 편집 버튼 클릭 리스너
        binding.btnPfEdit.setOnClickListener {

            // 프로필 편집 기능 실행
            val intent = Intent(context, EditMyPageFragment::class.java)
            startActivity(intent)
        }

        // 프로필 공유 버튼 클릭 리스너
        binding.btnPfShare.setOnClickListener {

            /*// 프로필 링크 생성
            val profileLink = "https://yourapp.com/profile/${viewModel.userId}" // 실제 앱의 프로필 링크로 수정

            // 클립보드에 링크 복사
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Profile Link", profileLink)
            clipboard.setPrimaryClip(clip)

            // 사용자에게 링크 복사 완료 알림
            Toast.makeText(requireContext(), "프로필 링크가 복사되었습니다.", Toast.LENGTH_SHORT).show()*/
        }

    }
}