package com.example.readme.ui.search.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.BookSearchResult
import com.example.readme.databinding.ItemSearchBookBinding

class SearchBookAdapter(
    private val onBookClick: (String) -> Unit
) : ListAdapter<BookSearchResult, SearchBookAdapter.BookViewHolder>(
    BookInfoDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class BookViewHolder(private val binding: ItemSearchBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookSearchResult) {
            binding.book = item
            binding.executePendingBindings()

            binding.bookItem.setOnClickListener {
                onBookClick(item.ISBN)
            }
        }
    }
}
