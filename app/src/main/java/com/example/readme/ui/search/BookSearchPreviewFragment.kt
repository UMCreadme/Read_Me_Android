package com.example.readme.ui.search

import android.os.Bundle
import android.view.View
import com.example.readme.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.databinding.FragmentSearchbookPreviewBinding
import com.example.whashow.base.BaseFragment

class BookSearchPreviewFragment : BaseFragment<FragmentSearchbookPreviewBinding>(R.layout.fragment_searchbook_preview) {
    private val bookSearchPreviewViewModel: BookSearchPreviewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = bookSearchPreviewViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set the layout manager for the RecyclerView
        binding.bookSearchesPreviewRecyclerView.layoutManager = LinearLayoutManager(context)

        // Set the adapter for the RecyclerView
        val adapter = SearchBookPreviewAdapter()
        binding.bookSearchesPreviewRecyclerView.adapter = adapter

        // Observe the search term list managed by the ViewModel
        setupObservers(adapter)

        // Retrieve the search keyword from the bundle and use it
        val keyword = arguments?.getString("keyword") ?: ""
        if (keyword.isNotEmpty()) {
            bookSearchPreviewViewModel.searchBook(keyword)
        }
    }

    private fun setupObservers(adapter: SearchBookPreviewAdapter) {
        // Observe the search term list managed by the ViewModel
        bookSearchPreviewViewModel.searchBookItems.observe(viewLifecycleOwner, Observer { items ->
            // Set the new data to the RecyclerView adapter
            adapter.submitList(items)
        })
    }
}
