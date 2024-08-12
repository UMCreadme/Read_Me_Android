package com.example.readme.ui.search.book

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.repository.SearchRepository
import com.example.readme.databinding.FragmentBookDetailBinding
import com.example.readme.ui.base.BaseFragment

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {

    val viewModel: BookDetailViewModel by viewModels {
        BookDetailViewModelFactory(SearchRepository)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set the layout manager for the RecyclerView
        binding.shortsPreviewRecyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // Set the adapter for the RecyclerView
        val adapter = BookDetailAdapter()
        binding.shortsPreviewRecyclerView.adapter = adapter

        val ISBN = arguments?.getString("ISBN")
        val bookId = arguments?.getInt("bookId")
        Log.d("BookDetailFragment", "ISBN: $ISBN")
        Log.d("BookDetailFragment", "bookId: $bookId")

        // Observe the shorts list managed by the ViewModel
        if (bookId != null && bookId != 0) {
            viewModel.getBookDetail(bookId)
        } else if (ISBN != null) {
            viewModel.getBookDetail(ISBN)
        }

        viewModel.bookDetail.observe(viewLifecycleOwner) {
            // Set the new data to the RecyclerView adapter
            binding.book = it
        }

        viewModel.shorts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // Scroll listener for pagination
        binding.shortsPreviewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount ?: 0

                if (lastVisibleItemPosition >= itemTotalCount - 1) {
                    // Load more shorts when reaching the end of the list
                    viewModel.loadMoreShorts()
                }
            }
        })

        // 읽음 버튼 리스너
        binding.readBtn.setOnClickListener {
            val bookId = viewModel.bookDetail.value?.bookId ?: 0
            if(bookId == 0) {
                val isbn = viewModel.bookDetail.value?.ISBN ?: ""
                viewModel.updateReadStatus(isbn)
            } else {
                viewModel.updateReadStatus(bookId)
            }
        }

        // 구매하기 버튼 리스너
        binding.buyButton.setOnClickListener {
            // 구매하기 링크로 이동
            val url = viewModel.bookDetail.value?.link ?: ""
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        // 뒤로가기 버튼 리스너
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }
}
