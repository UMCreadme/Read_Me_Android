package com.example.readme.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.databinding.TagItemBinding

class TagsAdapter(private val tags: List<String>) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    inner class TagViewHolder(private val binding: TagItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.tagText.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = TagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int = tags.size
}
