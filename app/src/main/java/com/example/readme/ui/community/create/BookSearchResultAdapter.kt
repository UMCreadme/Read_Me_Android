package com.example.readme.ui.community.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.BookSearchResult
import com.example.readme.databinding.SearchBookPreviewBinding

class BookSearchPreviewAdapter(
    private val onBookClick: (BookSearchResult) -> Unit
) : ListAdapter<BookSearchResult, BookSearchPreviewAdapter.BookViewHolder>(
    BookInfoDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = SearchBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class BookViewHolder(private val binding: SearchBookPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookSearchResult) {
            binding.item = item
            binding.executePendingBindings()

            binding.searchBookPreview.setOnClickListener {
                onBookClick(item)
            }
        }
    }
}

class BookInfoDiffCallback : DiffUtil.ItemCallback<BookSearchResult>() {
    override fun areItemsTheSame(oldItem: BookSearchResult, newItem: BookSearchResult): Boolean {
        return oldItem.ISBN == newItem.ISBN
    }

    override fun areContentsTheSame(oldItem: BookSearchResult, newItem: BookSearchResult): Boolean {
        return oldItem == newItem
    }
}
