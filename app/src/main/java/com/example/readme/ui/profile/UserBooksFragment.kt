package com.example.readme.ui.profile

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
import com.example.readme.ui.mypage.MyBooksAdapter
import com.example.readme.ui.search.book.BookDetailActivity
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch

class UserBooksFragment(private val userId: Int) : Fragment(R.layout.fragment_tab_recyclerview) {

    private lateinit var binding: FragmentTabRecyclerviewBinding
    private lateinit var booksAdapter: UserBooksAdapter
    private val booksList = mutableListOf<Book>()

    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabRecyclerviewBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner

         booksAdapter = UserBooksAdapter(booksList) { book ->
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
                val booksResponse = apiService.getBooks(userId)
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
