import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.databinding.FragmentFeedCommentBinding

import com.example.readme.ui.home.Feed.comment.Comment
import com.example.readme.ui.home.Feed.comment.CommentViewModelFactory
import com.example.readme.utils.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFeedCommentBinding? = null
    private val binding get() = _binding!!

    private lateinit var commentAdapter: CommentAdapter
    private val commentListService = RetrofitClient.getCommentListService()
    private val commentViewModel: CommentViewModel by viewModels {
        CommentViewModelFactory(commentListService)
    }
    private var shortsId: String? = null

    companion object {
        private const val ARG_SHORTS_ID = "shorts_id"

        fun newInstance(shortsId: String): CommentFragment {
            val fragment = CommentFragment()
            val args = Bundle()
            args.putString(ARG_SHORTS_ID, shortsId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shortsId = arguments?.getString(ARG_SHORTS_ID)
    }

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

        // shortsId가 null이 아닌지 확인하고 댓글 리스트를 가져오는 요청을 보냅니다.
        shortsId?.let {
            commentViewModel.fetchCommentList(it, 1, 20) // 1페이지, 20개씩 가져오기
        } ?: run {
            Toast.makeText(requireContext(), "잘못된 쇼츠 ID입니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.commentSave.setOnClickListener {
            val commentText = binding.commentEt.text.toString()
            if (commentText.isNotEmpty()) {
                shortsId?.let {
                    val newComment = Comment(
                        userId = 123,  // 예시로 고정된 유저 ID
                        account = "사용자 닉네임",  // 예시로 고정된 닉네임
                        profileImg = "https://example.com/profile.jpg",  // 예시로 고정된 이미지 URL
                        content = commentText,
                        passedDate = "방금"
                    )

                    // 새로운 댓글을 서버에 전송하고 리스트 갱신
                    commentViewModel.addComment(it, newComment)
                    binding.commentEt.text.clear()
                } ?: run {
                    Toast.makeText(requireContext(), "쇼츠 ID를 확인하세요.", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(requireContext(), "댓글을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        commentViewModel.commentList.observe(viewLifecycleOwner) { comments ->
            commentAdapter.submitList(comments)
            binding.rvComment.scrollToPosition(comments.size - 1) // 최신 댓글로 스크롤
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
        commentAdapter = CommentAdapter(binding.rvComment)
        binding.rvComment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
