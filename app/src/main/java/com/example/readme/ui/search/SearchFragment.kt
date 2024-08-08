package com.example.readme.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.whashow.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val recentSearchViewModel: RecentSearchViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowSearch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = recentSearchViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView 레이아웃 매니저 설정
        binding.recentSearchesRecyclerView.layoutManager = LinearLayoutManager(context)

        // RecyclerView 어댑터 설정
        val adapter = RecentSearchAdapter()
        binding.recentSearchesRecyclerView.adapter = adapter

        // ViewModel의 LiveData를 관찰하여 UI 업데이트
        setupObservers(adapter)
    }

    private fun setupObservers(adapter: RecentSearchAdapter) {
        // ViewModel에서 관리하는 검색어 리스트 관찰
        recentSearchViewModel.recentSearchItems.observe(viewLifecycleOwner, Observer { items ->
            // 검색어 리스트가 비어있는지 확인하고, 타이틀 가시성 조절
            if (items.isEmpty()) {
                binding.recentSearchesTitle.visibility = View.GONE
            } else {
                binding.recentSearchesTitle.visibility = View.VISIBLE
            }

            // RecyclerView 어댑터에 새로운 데이터 설정
            adapter.submitList(items)
        })
    }
}
