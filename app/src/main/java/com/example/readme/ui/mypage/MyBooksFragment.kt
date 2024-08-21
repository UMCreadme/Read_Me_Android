package com.example.readme.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.data.remote.Book
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentTabRecyclerviewBinding
import com.example.readme.ui.search.book.BookDetailActivity
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch

class MyBooksFragment : Fragment(R.layout.fragment_tab_recyclerview) {

    private lateinit var binding: FragmentTabRecyclerviewBinding
    private lateinit var booksAdapter: MyBooksAdapter
    private val booksList = mutableListOf<Book>()

    private val token: String = "example_your_token" // 토큰
    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabRecyclerviewBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner

        // 어댑터 초기화 및 클릭 이벤트 처리
        booksAdapter = MyBooksAdapter(booksList) { book ->
            val intent = Intent(requireContext(), BookDetailActivity::class.java).apply {
                putExtra("bookId", book.bookId)
            }
            startActivity(intent)
        }

        binding.recyclerView.adapter = booksAdapter

        // API 호출
        fetchBooks()
    }

    private fun fetchBooks() {
        lifecycleScope.launch {
            try {
                val booksResponse = apiService.getMyBooks(token)
                Log.d("UserShortsFragment", "Fetched shorts: ${booksResponse.result.size}")
                booksList.clear()
                booksList.addAll(booksResponse.result)
                booksAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // 오류 처리
                Log.e("UserShortsFragment", "Error fetching shorts", e)
            }
        }
    }

}