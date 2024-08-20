package com.example.readme.ui.home.make.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.data.entities.booklist.Book
import com.example.readme.databinding.FragmentBookSearchBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BookSearchFragment : BottomSheetDialogFragment(), RecentBookAdapter.MyItemClickListener,
    BookListAdapter.MyItemClickListener {

    private lateinit var binding: FragmentBookSearchBinding
    private lateinit var recentBookAdapter: RecentBookAdapter
    private lateinit var BookListAdapter: BookListAdapter

    private val viewModel: BookViewModel by viewModels()

    private var recentSearches = listOf<com.example.readme.data.entities.recentbook.Book>()
    private var BookSearches = listOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recentBookAdapter = RecentBookAdapter(recentSearches, true)
        recentBookAdapter.setMyItemClickListener(this)

        BookListAdapter = BookListAdapter(BookSearches)
        BookListAdapter.setMyItemClickListener(this)

        viewModel.fetchRecentBooks()

        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.adapter = recentBookAdapter

        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼을 눌렀을 때 처리
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // 검색어가 없을 때
                    binding.textView.visibility = View.VISIBLE
                    recentBookAdapter.updateList(recentSearches, true)
                    binding.rvSearch.adapter = recentBookAdapter
                } else {
                    // 검색어가 있을 때
                    binding.textView.visibility = View.GONE
                    viewModel.fetchBookList(newText)  // ViewModel을 통해 검색
                    BookListAdapter.updateList(BookSearches)
                    binding.rvSearch.adapter = BookListAdapter
                }
                return true
            }
        })

        // Observe changes in BookList LiveData
        viewModel.BookList.observe(viewLifecycleOwner) { bookList ->
            BookSearches = bookList
            if (binding.search.query.isNotEmpty()) {
                BookListAdapter.updateList(bookList)

            }
        }

        // Observe changes in RecentBooks LiveData (optional if you want to update recent searches dynamically)
        viewModel.recentBooks.observe(viewLifecycleOwner) { recentBooks ->
            recentSearches = recentBooks // 업데이트된 데이터를 저장합니다.
            if (binding.search.query.isEmpty()) {
                recentBookAdapter.updateList(recentSearches, true)
            }
        }
    }

    override fun onItemClick(book: com.example.readme.data.entities.recentbook.Book) {
        parentFragmentManager.setFragmentResult("requestKey", Bundle().apply {
            putString("bookTitle", book.title)
            putInt("ISBN", book.bookId)

        })
        dismiss()
    }

    override fun onDeleteClick(book: com.example.readme.data.entities.recentbook.Book) {
        // 현재는 onDeleteClick을 사용하는 로직이 없지만 필요 시 수정
    }

    override fun onItemClick(book: Book) {
        parentFragmentManager.setFragmentResult("requestKey", Bundle().apply {
            putString("bookTitle", book.bookTitle)
            putString("ISBN", book.isbn)
        })
        dismiss()
    }

    override fun onDeleteClick(book: Book) {
        //TODO("Not yet implemented")
    }
}
