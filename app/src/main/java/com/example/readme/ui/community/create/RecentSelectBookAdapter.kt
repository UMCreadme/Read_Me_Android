package com.example.readme.ui.community.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.recentbook.Book
import com.example.readme.databinding.SearchBookPreviewBinding

class RecentSelectBookAdapter(
    private val onBookClick: (Book) -> Unit
) : ListAdapter<Book, RecentSelectBookAdapter.RecentSelectBookViewHolder>(RecentSelectBookInfoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSelectBookViewHolder {
        val binding = SearchBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSelectBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentSelectBookViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class RecentSelectBookViewHolder(private val binding: SearchBookPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) {
            binding.selectedBook = item
            binding.executePendingBindings()

            binding.searchBookPreview.setOnClickListener {
                onBookClick(item)
            }
        }
    }
}

class RecentSelectBookInfoDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.bookId == newItem.bookId
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}