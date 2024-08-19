package com.example.readme.ui.search.shorts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.SearchShortsResult
import com.example.readme.databinding.FeedItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchShortsAdaptor : ListAdapter<SearchShortsResult, SearchShortsAdaptor.ShortsViewHolder>(
    ShortsInfoDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder {
        val binding = FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ShortsViewHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchShortsResult) {
            binding.shorts = item
            binding.likeCount.text = "좋아요 ${item.likeCnt}개"
            if(item.commentCnt == 0) {
                binding.commentTxt.visibility = ViewGroup.GONE
            } else {
                binding.commentTxt.text = "댓글 ${item.commentCnt}개 모두 보기"
            }
            binding.timestamp.text = getTimeAgo(item.postingDate)
            binding.executePendingBindings()
        }
    }

    fun getTimeAgo(dateString: String): String {
        val nowDateTime = Calendar.getInstance().timeInMillis
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREAN)
        val postingDateTime = df.parse(dateString)?.time ?: return ""

        val timeDiff = nowDateTime - postingDateTime

        val minutes = timeDiff / 60000
        val hours = minutes / 60
        val days = hours / 24

        Log.i("SearchShortsAdaptor", "postingDateTime: $dateString, nowDateTime: $nowDateTime")
        Log.i("SearchShortsAdaptor", "minutes: $minutes, hours: $hours, days: $days")

        return when {
            minutes < 1 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            else -> "${days}일 전"
        }
    }
}

class ShortsInfoDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<SearchShortsResult>() {
    override fun areItemsTheSame(oldItem: SearchShortsResult, newItem: SearchShortsResult): Boolean {
        return oldItem.shortsId == newItem.shortsId
    }

    override fun areContentsTheSame(oldItem: SearchShortsResult, newItem: SearchShortsResult): Boolean {
        return oldItem == newItem
    }
}