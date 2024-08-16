package com.example.readme.ui.search.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.ShortsPreview
import com.example.readme.databinding.ShortsPreviewBinding

class BookDetailAdapter: ListAdapter<ShortsPreview, BookDetailAdapter.ShortsViewHolder>(
    ShortsInfoDiffCallback()
) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder {
            val binding = ShortsPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShortsViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }

        inner class ShortsViewHolder(private val binding: ShortsPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ShortsPreview) {
                binding.shorts = item
                binding.executePendingBindings()
            }
        }
}

class ShortsInfoDiffCallback : DiffUtil.ItemCallback<ShortsPreview>() {
    override fun areItemsTheSame(oldItem: ShortsPreview, newItem: ShortsPreview): Boolean {
        return oldItem.shortsId == newItem.shortsId
    }

    override fun areContentsTheSame(oldItem: ShortsPreview, newItem: ShortsPreview): Boolean {
        return oldItem == newItem
    }
}