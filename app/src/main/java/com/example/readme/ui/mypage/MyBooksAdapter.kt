package com.example.readme.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.R
import com.example.readme.data.remote.Book
import com.example.readme.databinding.ItemShortsCardBinding

class MyBooksAdapter (private val booksList: List<Book>) : RecyclerView.Adapter<MyBooksAdapter.BookViewHolder>() {

    inner class BookViewHolder(private val binding: ItemShortsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.bookTitle.text = book.bookTitle
            binding.bookAuthor.text = book.bookAuthor

            if (book.bookImage.isNotEmpty()) {
                Glide.with(binding.shortsImage.context)
                    .load(book.bookImage)
                    .into(binding.shortsImage)
            } else {
                binding.shortsImage.setImageResource(R.drawable.img_profile_default)
                // 나중에 책 기본 이미지 추가!!!
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShortsCardBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(booksList[position])
    }

    override fun getItemCount(): Int = booksList.size
}