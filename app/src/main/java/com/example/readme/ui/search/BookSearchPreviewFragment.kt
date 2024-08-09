package com.example.readme.ui.search

import com.example.readme.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentSearchbookPreviewBinding
import com.example.readme.ui.base.BaseFragment
import com.example.readme.utils.RetrofitClient

class BookSearchPreviewFragment : BaseFragment<FragmentSearchbookPreviewBinding>(R.layout.fragment_searchbook_preview) {

    private val token: String = "" // 테스트 용
    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.apiService
    }
    val viewModel: BookSearchPreviewViewModel by viewModels {
        BookSearchPreviewViewModelFactory(token, apiService)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set the layout manager for the RecyclerView
        binding.bookSearchesPreviewRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // Set the adapter for the RecyclerView
        val adapter = SearchBookPreviewAdapter()
        binding.bookSearchesPreviewRecyclerView.adapter = adapter

        // Observe the search term list managed by the ViewModel
        viewModel.searchBookItems.observe(viewLifecycleOwner) {
            // Set the new data to the RecyclerView adapter
            adapter.submitList(it)
        }

        // Get the keyword from arguments and trigger search
        val keyword = arguments?.getString("keyword") ?: ""
        viewModel.onQueryTextChange(keyword)
    }
}
