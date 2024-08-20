package com.example.readme.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.readme.R
import com.example.readme.databinding.FragmentCommunityDetailBinding
import com.example.readme.ui.base.BaseFragment

class BookDetailFragment : BaseFragment<FragmentCommunityDetailBinding>(R.layout.fragment_community_detail) {

    private val viewModel: BookDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialization code if needed
    }

    override fun initStartView() {
        super.initStartView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Dynamically add tags
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
            // Handle chat button click
            val intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
        }
    }
}
