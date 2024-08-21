package com.example.readme.ui.community.explore

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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CommunityDetailFragment : BaseFragment<FragmentCommunityDetailBinding>(R.layout.fragment_community_detail) {

    private val viewModel: CommunityDetailViewModel by viewModels {
        CommunityDetailViewModelFactory(CommunityRepository)
    }

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
    }

    private fun updateUI(community: CommunityDetailResponse?) {
        community?.let {
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
            } else {
                binding.btnGoToChat.text = "가입하기"
            }

            addTagsToFlowLayout(binding.tagsFlow, it.tags)
        }
    }

    private fun getTimeAgo(dateString: String): String {
        val nowDateTime = Calendar.getInstance().timeInMillis
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREAN)
        val postingDateTime = df.parse(dateString)?.time ?: return ""

        val timeDiff = nowDateTime - postingDateTime

        val minutes = timeDiff / 60000
        val hours = minutes / 60
        val days = hours / 24

        return when {
            minutes < 1 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            else -> "${days}일 전"
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