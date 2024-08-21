package com.example.readme.ui.community.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.readme.R
import com.example.readme.data.entities.CommunityDetailResponse
import com.example.readme.data.repository.CommunityRepository
import com.example.readme.databinding.FragmentCommunityDetailBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.home.make.preview.FlowLayout
import com.example.readme.ui.profile.UserProfileFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class CommunityDetailFragment : BaseFragment<FragmentCommunityDetailBinding>(R.layout.fragment_community_detail) {

    private val viewModel: CommunityDetailViewModel by viewModels {
        CommunityDetailViewModelFactory(CommunityRepository)
    }

    private var userId: Int = 0

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // communityId가 존재할 경우에만 데이터를 가져옴
        arguments?.getInt("communityId")?.let {
            viewModel.fetchCommunityDetail(it)
        }

        viewModel.communityDetail.observe(this) { community ->
            updateUI(community)
        }

        binding.btnGoToChat.setOnClickListener {
            // 참여중일 경우
            if(binding.btnGoToChat.text == "채팅하기") {
                // 채팅방으로 이동 - 서비스 준비중 토스팅 알림
                Toast.makeText(context, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 가입하기
                viewModel.joinCommunity(arguments?.getInt("communityId") ?: 0)
            }
        }

        binding.userLayout.setOnClickListener {
            if(userId != 0) {
                // 유저 프로필로 이동
                // 사용자 상세 화면으로 전환
                val userProfileFragment = UserProfileFragment()
                val bundle = Bundle().apply {
                    putInt("userId", userId)
                }
                userProfileFragment.arguments = bundle
                (activity as MainActivity).addFragment(userProfileFragment)
            }
        }

        viewModel.errMessage.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(community: CommunityDetailResponse?) {
        community?.let {
            userId = it.leader.userId
            // 이미지 리소스 설정
            binding.bookTitle.text = it.book.title
            binding.bookAuthor.text = it.book.author

            Glide.with(this)
                .load(it.book.imageUrl)
                .placeholder(R.drawable.img_1)
                .into(binding.bookCover)

            Glide.with(this)
                .load(it.leader.imageUrl)
                .placeholder(R.drawable.img_1)
                .into(binding.userProfileImg)

            binding.userName.text = it.leader.account
            binding.userNickname.text = it.leader.nickname
            binding.userLocation.text = it.location
            binding.writeTime.text = getTimeAgo(it.createdAt)
            binding.userContent.text = it.content

            binding.capacity.text = "${it.currentMembers} / ${it.capacity}"
            if(it.isParticipating) {
                binding.btnGoToChat.text = "채팅하기"
                binding.btnGoToChat.isEnabled = true
            } else if(it.capacity == it.currentMembers) {
                binding.btnGoToChat.text = "마감"
                binding.btnGoToChat.isEnabled = false

            } else {
                binding.btnGoToChat.text = "가입하기"
                binding.btnGoToChat.isEnabled = true
            }

            addTagsToFlowLayout(binding.tagsFlow, it.tags)
        }
    }

    private fun getTimeAgo(dateString: String): String {
        // 현재 시간을 KST로 설정
        val nowDateTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).timeInMillis

        // UTC로 설정된 SimpleDateFormat
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREAN).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        // 서버에서 받은 UTC 시간 파싱
        val postingDateTime = df.parse(dateString)?.time ?: return ""

        // KST로 변환된 현재 시간과의 차이 계산
        val timeDiff = nowDateTime - postingDateTime

        val minutes = timeDiff / 60000
        val hours = minutes / 60
        val days = hours / 24

        return when {
            minutes < 1 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            days <= 6 -> "${days}일 전"
            else -> {
                // 날짜를 KST로 포맷
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).apply {
                    timeZone = TimeZone.getTimeZone("Asia/Seoul")
                }
                val postingDate = Calendar.getInstance().apply { timeInMillis = postingDateTime }
                dateFormat.format(postingDate.time)
            }
        }
    }

    private fun addTagsToFlowLayout(layout: FlowLayout, tags: List<String>) {
        layout.removeAllViews()  // 기존 태그 제거

        layout.horizontalSpacing = 16
        layout.verticalSpacing = 16

        for (tag in tags) {
            val tagTextView = LayoutInflater.from(layout.context).inflate(R.layout.item_community_tag, layout, false) as TextView
            tagTextView.text = tag
            layout.addView(tagTextView)
        }
    }
}