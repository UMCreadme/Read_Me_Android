package com.example.readme.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.data.remote.Book
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentTabRecyclerviewBinding
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch

class UserBooksFragment : Fragment(R.layout.fragment_tab_recyclerview) {

    private lateinit var binding: FragmentTabRecyclerviewBinding
    private lateinit var booksAdapter: UserBooksAdapter
    private val booksList = mutableListOf<Book>()

    private val userId: Int = 3 // 테스트용 사용자 ID
    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabRecyclerviewBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        booksAdapter = UserBooksAdapter(booksList)
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
