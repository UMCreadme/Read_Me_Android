package com.example.readme.ui.home.make.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.databinding.ItemBookBinding
import com.example.readme.data.entities.booklist.Book

class BookListAdapter(var list: List<Book>) : RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    interface MyItemClickListener {
        fun onItemClick(book: Book)
        fun onDeleteClick(book: Book)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: BookSearchFragment) {
        myItemClickListener = itemClickListener
    }

    inner class BookHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        val bookImage = binding.bookImage
        val title = binding.title
        val author = binding.author
        val del = binding.btnDel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = list[position]

        // 이미지 로딩 (Glide 사용)
        Glide.with(holder.itemView.context)
            .load(book.bookCover)
            .into(holder.bookImage)

        holder.title.text = book.bookTitle
        holder.author.text = book.author

        holder.itemView.setOnClickListener { myItemClickListener.onItemClick(book) }
        holder.del.setOnClickListener { myItemClickListener.onDeleteClick(book) }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newItems: List<Book>) {
        this.list = newItems
        notifyDataSetChanged()
    }

    fun deleteItem(book: Book) {
        this.list = list.filter { it != book }
        notifyDataSetChanged()
    }
}
