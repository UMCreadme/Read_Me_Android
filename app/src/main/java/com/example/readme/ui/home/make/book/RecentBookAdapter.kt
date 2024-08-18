package com.example.readme.ui.home.make.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.databinding.ItemBookBinding
import com.example.readme.data.entities.recentbook.Book

class RecentBookAdapter(var list: List<Book>, private var isRecent: Boolean = true) : RecyclerView.Adapter<RecentBookAdapter.BookHolder>() {

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
            .load(book.imageUrl)
            .into(holder.bookImage)

        holder.title.text = book.title
        holder.author.text = book.author

        holder.itemView.setOnClickListener { myItemClickListener.onItemClick(book) }
        holder.del.setOnClickListener { myItemClickListener.onDeleteClick(book) }

        // 최근 검색된 항목인 경우 삭제 버튼 표시
        if (isRecent) {
            holder.del.visibility = View.VISIBLE
        } else {
            holder.del.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newItems: List<Book>, isRecent: Boolean) {
        this.list = newItems
        this.isRecent = isRecent
        notifyDataSetChanged()
    }

    fun deleteItem(book: Book) {
        this.list = list.filter { it != book }
        notifyDataSetChanged()
    }
}
