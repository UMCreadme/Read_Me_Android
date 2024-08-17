package com.example.readme.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.RecentSearch
import com.example.readme.databinding.RecentSearchBookBinding
import com.example.readme.databinding.RecentSearchQueryBinding

class RecentSearchAdapter(
    private val onBookClick: (Int) -> Unit,
    private val onQueryClick: (String) -> Unit,
    private val onDeleteClick: (RecentSearch) -> Unit
) : ListAdapter<RecentSearch, RecyclerView.ViewHolder>(RecentSearchDiffCallback()) {

    companion object {
        private const val TYPE_QUERY = 0
        private const val TYPE_BOOK = 1
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.bookId == null) TYPE_QUERY else TYPE_BOOK
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BOOK -> {
                val binding = RecentSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookViewHolder(binding)
            }
            TYPE_QUERY -> {
                val binding = RecentSearchQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                QueryViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder) {
                is BookViewHolder -> holder.bind(item)
                is QueryViewHolder -> holder.bind(item)
            }
        }
    }

    inner class BookViewHolder(private val binding: RecentSearchBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearch) {
            binding.item = item
            binding.executePendingBindings()

            // Click listener for book items
            binding.bookInfo.setOnClickListener {
                item.bookId?.let { bookId -> onBookClick(bookId) }
            }

            // Click listener for delete button
            binding.deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    inner class QueryViewHolder(private val binding: RecentSearchQueryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearch) {
            binding.item = item
            binding.executePendingBindings()

            // Click listener for query items
            binding.queryText.setOnClickListener {
                onQueryClick(item.query)
            }

            // Click listener for delete button
            binding.deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }
}

class RecentSearchDiffCallback : DiffUtil.ItemCallback<RecentSearch>() {
    override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
        return oldItem.recentSearchesId == newItem.recentSearchesId
    }

    override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
        return oldItem == newItem
    }
}


