package com.example.readme.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.databinding.FragmentSearchUserBinding
import com.example.whashow.base.BaseFragment

class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>(R.layout.fragment_search_user) {
    private val searchUserViewModel: SearchUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = searchUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView에 LinearLayoutManager 설정
        binding.searchUserRecyclerView.layoutManager = LinearLayoutManager(context)

        // RecyclerView에 어댑터 설정
        val adapter = SearchUserAdaptor()
        binding.searchUserRecyclerView.adapter = adapter

        // ViewModel의 데이터 관찰하여 RecyclerView 업데이트
        setupObservers(adapter)

        // Bundle로 전달된 검색어를 가져와서 사용
        val keyword = arguments?.getString("keyword") ?: ""
        if (keyword.isNotEmpty()) {
            searchUserViewModel.searchUser(keyword)
        }
    }

    private fun setupObservers(adapter: SearchUserAdaptor) {
        // ViewModel에서 관리하는 사용자 목록을 관찰하고, RecyclerView 어댑터에 새로운 데이터 설정
        searchUserViewModel.searchUserItems.observe(viewLifecycleOwner, Observer { items ->
            adapter.submitList(items)
        })
    }
}