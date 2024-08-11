package com.example.readme.ui.search.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.BookInfo
import com.example.readme.databinding.SearchBookPreviewBinding

class SearchBookPreviewAdapter : ListAdapter<BookInfo, SearchBookPreviewAdapter.BookViewHolder>(
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
        fun bind(item: BookInfo) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}

class BookInfoDiffCallback : DiffUtil.ItemCallback<BookInfo>() {
    override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
        return oldItem.ISBN == newItem.ISBN
    }

    override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
        return oldItem == newItem
    }
}
