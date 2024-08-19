package com.example.readme.ui.search.book

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.repository.SearchRepository
import com.example.readme.databinding.FragmentSearchBookBinding
import com.example.readme.ui.base.BaseFragment

class SearchBookFragment : BaseFragment<FragmentSearchBookBinding>(R.layout.fragment_search_book) {

    private val viewModel: SearchBookViewModel by viewModels {
        SearchBookViewModelFactory(SearchRepository)
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView에 GridLayoutManager 설정 (한 줄에 2개의 아이템 배치)
        binding.searchBookRecyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // RecyclerView에 어댑터 설정
        val adapter = SearchBookAdapter(
            onBookClick = { ISBN ->
                // 책 상세 화면으로 전환
                val intent = Intent(requireActivity(), BookDetailActivity::class.java)
                intent.apply {
                    this.putExtra("ISBN", ISBN)
                }
                startActivity(intent)
            }
        )
        binding.searchBookRecyclerView.adapter = adapter

        // Bundle로 전달된 검색어를 가져와서 사용
        val keyword = arguments?.getString("keyword") ?: ""
        if (keyword.isNotEmpty()) {
            viewModel.searchBook(keyword) // 초기 검색 수행

            viewModel.searchBookItems.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }

        // RecyclerView의 스크롤 상태를 감지하여 페이지네이션 적용
        binding.searchBookRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // 3/4 시점에 도달하면 다음 페이지를 로드
                val threshold = (totalItemCount * 3) / 4
                if ((visibleItemCount + firstVisibleItemPosition) >= threshold &&
                    firstVisibleItemPosition >= 0
                ) {
                    viewModel.loadNextPage()
                }
            }
        })
    }
}
