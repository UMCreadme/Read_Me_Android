package com.example.readme.ui.community.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.databinding.FragmentCommunityDetailBinding
import com.example.readme.ui.community.Community
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommunityDetailFragment : Fragment() {

    private var communityId: Int? = null
    private lateinit var binding: FragmentCommunityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            communityId = it.getInt("communityId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // communityId가 존재할 경우에만 데이터를 가져옴
        communityId?.let {
            fetchCommunityDetails(it)
        }
    }

    private fun fetchCommunityDetails(id: Int) {
        lifecycleScope.launch {
            try {
                val service = RetrofitClient.getReadmeServerService()
                val response = withContext(Dispatchers.IO) {
                    service.getCommunityDetail(id)
                }

                if (response.isSuccess) {
                    val community = response.result
                    updateUI(community)
                } else {
                    // 실패 메시지 처리
                }
            } catch (e: Exception) {
                // 예외 처리
            }
        }
    }

    private fun updateUI(community: Community?) {
        community?.let {
            // 이미지 리소스 설정
            binding.userProfileImg.setImageResource(it.imageResId)

            // 제목, 저자, 위치, 책 커버, 닉네임, 내용, 생성일 설정
            binding.bookTitle.text = it.title
            binding.bookAuthor.text = it.author
            binding.userLocation.text = it.location
            binding.bookCover.setImageResource(it.bookCover)
            binding.userNickname.text = it.userNickname
            binding.userComment.text = it.content
            binding.capacity.text = "${it.currentMembers}/${it.totalMembers}"

            // 태그 추가
//            context?.let { ctx ->
//                binding.tagsContainer.removeAllViews()
//                it.tags.forEach { tag ->
//                    val tagView = LayoutInflater.from(ctx).inflate(R.layout.item_tag, binding.tagsContainer, false) as TextView
//                    tagView.text = tag
//                    binding.tagsContainer.addView(tagView)
//                }
//            }
        }
    }
}
