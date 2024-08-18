package com.example.readme.ui.community

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.readme.R
import com.example.readme.databinding.FragmentBookDetailBinding
import com.example.readme.ui.base.BaseFragment

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {

    private val viewModel: BookDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 초기화 작업
    }

    override fun initStartView() {
        super.initStartView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // 태그 동적으로 추가
        viewModel.tags.observe(viewLifecycleOwner) { tags ->
            binding.tagsContainer.removeAllViews() // Clear existing views
            val tagIds = mutableListOf<Int>()
            for (tag in tags) {
                val tagView = TextView(requireContext()).apply {
                    id = View.generateViewId()
                    text = tag
                    setPadding(8, 4, 8, 4)
                    setBackgroundResource(R.drawable.tag_background)
                }
                binding.tagsContainer.addView(tagView)
                tagIds.add(tagView.id)
            }
            // Assuming you're using ConstraintLayout's Flow
            binding.tagsFlow.setReferencedIds(tagIds.toIntArray())
        }

        binding.chatButton.setOnClickListener {
            // 채팅하기 버튼 클릭 이벤트 처리
        }
    }
}
