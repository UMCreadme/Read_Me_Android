package com.example.readme.ui.community.create

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.data.repository.SearchRepository
import com.example.readme.databinding.FragmentRecentSelectBookBinding
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
            onBookClick = { bookId ->
//                // TODO: 모임 생성 화면으로 전환
//                val bookDetailFragment = BookDetailFragment().apply{
//                    arguments = Bundle().apply {
//                        putInt("bookId", bookId)
//                    }
//                }
//                (activity as MainActivity).addFragment(bookDetailFragment)
            }
        )

        binding.recentSelectBookRecyclerView.adapter = adapter

        // RecentSelectBook 목록 갱신
        viewModel.fetchRecentSelectBookItems().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}