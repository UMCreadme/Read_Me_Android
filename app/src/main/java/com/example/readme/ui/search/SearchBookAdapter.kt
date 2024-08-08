package com.example.readme.ui.search

import BookInfoDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.BookInfo
import com.example.readme.databinding.ItemSearchBookBinding

class SearchBookAdapter : ListAdapter<BookInfo, SearchBookAdapter.BookViewHolder>(BookInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookViewHolder(private val binding: ItemSearchBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookInfo) {
            binding.book = item
            binding.executePendingBindings()
        }
    }
}
