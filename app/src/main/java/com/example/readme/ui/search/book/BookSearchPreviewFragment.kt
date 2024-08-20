package com.example.readme.ui.search.book

import android.os.Bundle
import com.example.readme.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.repository.SearchRepository
import com.example.readme.databinding.FragmentSearchbookPreviewBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class BookSearchPreviewFragment : BaseFragment<FragmentSearchbookPreviewBinding>(R.layout.fragment_searchbook_preview) {

    val viewModel: BookSearchPreviewViewModel by viewModels {
        BookSearchPreviewViewModelFactory(SearchRepository)
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
        val adapter = SearchBookPreviewAdapter(
            onBookClick = { ISBN ->
                // 책 상세 화면으로 전환
                val bookDetailFragment = BookDetailFragment()
                val bundle = Bundle()
                bundle.putString("ISBN", ISBN)
                bookDetailFragment.arguments = bundle
                (activity as MainActivity).addFragment(bookDetailFragment)
                viewModel.saveRecentSearch(ISBN)
            }
        )
        binding.bookSearchesPreviewRecyclerView.adapter = adapter

        // Observe the search term list managed by the ViewModel
        viewModel.searchBookItems.observe(viewLifecycleOwner) {
            // Set the new data to the RecyclerView adapter
            adapter.submitList(it)
        }

        // Get the keyword from arguments and trigger search
        val keyword = arguments?.getString("keyword") ?: ""
        viewModel.onQueryTextChange(keyword)

        // Add scroll listener for pagination
        binding.bookSearchesPreviewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                        // Load more data if the end of the list is reached
                        viewModel.loadMore()
                    }
                }
            }
        })
    }
}
