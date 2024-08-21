package com.example.readme.ui.community.create

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.data.repository.SearchRepository
import com.example.readme.databinding.FragmentRecentSelectBookBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class RecentSelectBookFragment : BaseFragment<FragmentRecentSelectBookBinding>(R.layout.fragment_recent_select_book) {
    private val viewModel: RecentSelectBookViewModel by viewModels {
        RecentSelectBookViewModelFactory(SearchRepository)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView 레이아웃 매니저 설정
        binding.recentSelectBookRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // RecyclerView 어댑터 설정
        val adapter = RecentSelectBookAdapter(
            onBookClick = { book ->
                val communityCreateFragment = CommunityCreateFragment()
                val bundle = Bundle().apply {
                    putString("bookCover", book.imageUrl)
                    putString("bookTitle", book.title)
                    putString("ISBN", book.isbn)
                    putString("author", book.author)
                }
                communityCreateFragment.arguments = bundle
                (activity as MainActivity).addFragment(communityCreateFragment)
            }
        )

        binding.recentSelectBookRecyclerView.adapter = adapter

        // RecentSelectBook 목록 갱신
        viewModel.fetchRecentSelectBookItems().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}