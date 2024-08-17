package com.example.readme.ui.search.book

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.repository.SearchRepository

class BookDetailActivity : AppCompatActivity(R.layout.activity_book_detail) {

    private val viewModel: BookDetailViewModel by viewModels {
        BookDetailViewModelFactory(SearchRepository)
    }

    private lateinit var bookDetailAdapter: BookDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ISBN = intent.getStringExtra("ISBN")
        val bookId = intent.getIntExtra("bookId", 0)

        // Initialize the RecyclerView and adapter
        bookDetailAdapter = BookDetailAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.shorts_preview_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = bookDetailAdapter

        // Observe data from ViewModel
        if (bookId != 0) {
            viewModel.getBookDetail(bookId)
        } else if (!ISBN.isNullOrEmpty()) {
            viewModel.getBookDetail(ISBN)
        }

        viewModel.shorts.observe(this) { shorts ->
            bookDetailAdapter.submitList(shorts)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        // Set click listeners for buttons
        val readButton = findViewById<SwitchCompat>(R.id.read_btn)
        readButton.setOnClickListener {
            val bookId = viewModel.bookDetail.value?.bookId ?: 0
            if (bookId == 0) {
                val isbn = viewModel.bookDetail.value?.ISBN ?: ""
                viewModel.updateReadStatus(isbn)
            } else {
                viewModel.updateReadStatus(bookId)
            }
        }

        val buyButton = findViewById<Button>(R.id.buy_button)
        buyButton.setOnClickListener {
            // Navigate to purchase link
            val url = viewModel.bookDetail.value?.link ?: ""
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        val backButton = findViewById<ImageButton>(R.id.book_detail_back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}
