package com.example.readme.ui.search

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.data.repository.SearchRepository
import com.example.readme.databinding.FragmentRecentSearchBinding
import com.example.readme.ui.base.BaseFragment

class RecentSearchFragment : BaseFragment<FragmentRecentSearchBinding>(R.layout.fragment_recent_search) {
    private val viewModel: RecentSearchViewModel by viewModels {
        RecentSearchViewModelFactory(SearchRepository)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView 레이아웃 매니저 설정
        binding.recentSearchesRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // RecyclerView 어댑터 설정
        val adapter = RecentSearchAdapter()
        binding.recentSearchesRecyclerView.adapter = adapter

        // RecentSearch 목록 갱신
        viewModel.fetchRecentSearchItems().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}