package com.example.readme.ui.home.Feed.comment

import CommentViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.data.remote.Comment
import com.example.readme.databinding.FragmentFeedCommentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFeedCommentBinding? = null
    private val binding get() = _binding!!

    private lateinit var commentAdapter: CommentAdapter
    private val commentViewModel: CommentViewModel by activityViewModels()

    private val shortsId : String = "159"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BottomSheetBehavior 설정
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.peekHeight = 800 // 원하는 높이로 설정
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        setupRecyclerView()

        // 초기 댓글 리스트를 가져옴
        commentViewModel.fetchChatList(shortsId , 1, 20) // 예시로 "159" ID 사용, 실제 데이터에 맞게 변경 필요

        binding.commentSave.setOnClickListener {
            val commentText = binding.commentEt.text.toString()
            if (commentText.isNotEmpty()) {
                val newComment = Comment(
                    userId = 123,  // 예시로 고정된 유저 ID
                    nickname = "사용자 닉네임",  // 예시로 고정된 닉네임
                    profileImg = "https://example.com/profile.jpg",  // 예시로 고정된 이미지 URL
                    content = commentText,
                    passedDate = "방금"
                )

                // 새로운 댓글을 서버에 전송하고 리스트 갱신
                commentViewModel.addComment("159", newComment) // shortsId는 예시로 "159" 사용
                binding.commentEt.text.clear()
            } else {
                Toast.makeText(requireContext(), "댓글을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        commentViewModel.commentList.observe(viewLifecycleOwner) { comments ->
            commentAdapter.submitList(comments)
            binding.rvSearch.scrollToPosition(comments.size - 1) // 최신 댓글로 스크롤
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        commentAdapter = CommentAdapter()
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
