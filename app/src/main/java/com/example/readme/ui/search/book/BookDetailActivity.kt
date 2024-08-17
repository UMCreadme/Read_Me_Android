package com.example.readme.ui.search.book

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

        val readButton = findViewById<SwitchCompat>(R.id.read_btn)
        val buyButton = findViewById<Button>(R.id.buy_button)

        viewModel.bookDetail.observe(this) { bookDetail ->
            // Set the book detail information
            if (bookDetail != null) {
                Glide.with(this)
                    .load(bookDetail.bookImg)
                    .placeholder(R.drawable.ic_book_placeholder)
                    .into(findViewById(R.id.book_cover_image))
                readButton.isChecked = bookDetail.isRead
                buyButton.isEnabled = bookDetail.link.isNotEmpty()
                findViewById<TextView>(R.id.book_title).text = bookDetail.title
                findViewById<TextView>(R.id.author_info).text = bookDetail.author
            }
        }

        viewModel.toastMessage.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
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
        readButton.setOnClickListener {
            val bookId = viewModel.bookDetail.value?.bookId ?: 0
            if (bookId == 0) {
                val isbn = viewModel.bookDetail.value?.ISBN ?: ""
                viewModel.updateReadStatus(isbn)
            } else {
                viewModel.updateReadStatus(bookId)
            }
        }

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

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
