package com.example.readme.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.data.entities.ShortsItem
import com.example.readme.databinding.ItemShortsCardBinding

class MyShortsAdapter (private val shortsList: List<ShortsItem>) : RecyclerView.Adapter<MyShortsAdapter.ShortsViewHolder>() {

    inner class ShortsViewHolder(private val binding: ItemShortsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shorts: ShortsItem) {
            binding.bookTitle.text = shorts.shortsBookTitle
            binding.bookAuthor.text = shorts.shortsBookAuthor

            if (shorts.shortsImage.isNotEmpty()) {
                Glide.with(binding.shortsImage.context)
                    .load(shorts.shortsImage)
                    .into(binding.shortsImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShortsCardBinding.inflate(inflater, parent, false)
        return ShortsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        holder.bind(shortsList[position])
    }

    override fun getItemCount(): Int = shortsList.size
}