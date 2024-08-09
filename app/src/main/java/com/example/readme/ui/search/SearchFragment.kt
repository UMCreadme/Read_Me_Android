package com.example.readme.ui.search

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.utils.RetrofitClient

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val token: String = "" // 테스트 용
    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.apiService
    }
    private val viewModel: RecentSearchViewModel by viewModels {
        RecentSearchViewModelFactory(token, apiService)
    }

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowSearch()
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
