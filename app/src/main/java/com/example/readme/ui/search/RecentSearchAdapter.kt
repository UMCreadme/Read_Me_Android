package com.example.readme.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.readme.R
import com.example.readme.data.entities.RecentSearch
import com.example.readme.databinding.RecentSearchBookBinding
import com.example.readme.databinding.RecentSearchQueryBinding

class RecentSearchAdapter : ListAdapter<RecentSearch, RecyclerView.ViewHolder>(RecentSearchDiffCallback()) {

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
        }
    }

    inner class QueryViewHolder(private val binding: RecentSearchQueryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearch) {
            binding.item = item
            binding.executePendingBindings()
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

// BindingAdapter for loading images using Glide TODO: BindingAdapter 다른 파일로 이동
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_book_cover)  // 로딩 중 표시할 이미지
                .error(R.drawable.ic_book_placeholder)  // 에러 시 표시할 이미지
                .override(Target.SIZE_ORIGINAL)  // 이미지의 원본 크기 유지
                .diskCacheStrategy(DiskCacheStrategy.ALL)  // 디스크 캐시 전략 설정
                .priority(Priority.HIGH)  // 우선 순위 높게 설정
                .dontTransform())  // 기본적인 변환 방지 (이미지 왜곡 방지)
            .into(view)
    }
}

