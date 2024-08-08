package com.example.readme.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.readme.R
import com.example.readme.databinding.FragmentSearchBookBinding
import com.example.whashow.base.BaseFragment

class SearchBookFragment : BaseFragment<FragmentSearchBookBinding>(R.layout.fragment_search_book) {
    private val searchBookViewModel: SearchBookViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = searchBookViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView에 GridLayoutManager 설정 (한 줄에 2개의 아이템 배치)
        binding.searchBookRecyclerView.layoutManager = GridLayoutManager(context, 2)

        // RecyclerView에 어댑터 설정
        val adapter = SearchBookAdapter()
        binding.searchBookRecyclerView.adapter = adapter

        // ViewModel의 데이터 관찰하여 RecyclerView 업데이트
        setupObservers(adapter)

        // Bundle로 전달된 검색어를 가져와서 사용
        val keyword = arguments?.getString("keyword") ?: ""
        if (keyword.isNotEmpty()) {
            searchBookViewModel.searchBook(keyword)
        }
    }

    private fun setupObservers(adapter: SearchBookAdapter) {
        // ViewModel에서 관리하는 책 목록을 관찰하고, RecyclerView 어댑터에 새로운 데이터 설정
        searchBookViewModel.searchBookItems.observe(viewLifecycleOwner, Observer { items ->
            adapter.submitList(items)
        })
    }
}
