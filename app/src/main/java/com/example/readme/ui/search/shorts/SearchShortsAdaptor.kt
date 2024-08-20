package com.example.readme.ui.search.shorts

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.readme.R
import com.example.readme.data.entities.SearchShortsResult
import com.example.readme.databinding.FeedItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchShortsAdaptor(
    private val viewModel: SearchShortsViewModel,
    private val onShortsClick: (Int) -> Unit
) : ListAdapter<SearchShortsResult, SearchShortsAdaptor.ShortsViewHolder>(
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
            // 유저 정보 세팅
            Glide.with(binding.root.context)
                .load(item.profileImg)
                .circleCrop()
                .into(binding.feedProfile)
            binding.username.text = item.nickname

            // 쇼츠 정보 세팅 (이미지 & 구절)
            Glide.with(binding.root.context)
                .load(item.shortsImg)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        binding.shortsImage.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Called when the resource is no longer needed, here you can clear the background if needed
                    }
                })
            binding.feedSentence.text = item.phrase

            // 좋아요 버튼
            Log.i("SearchShortsAdaptor", "item.isLike: ${item.isLike}")
            if(item.isLike) {
                binding.likeIcon.setImageResource(R.drawable.likefill_icon)
                binding.likefillIcon.visibility = View.GONE
            } else {
                binding.likeIcon.setImageResource(R.drawable.like_icon)
                binding.likefillIcon.visibility = View.GONE
            }

            binding.likeIcon.setOnClickListener {
                // 좋아요 상태를 업데이트
                item.isLike = !item.isLike
                item.likeCnt += if (item.isLike) 1 else -1

                // 어댑터의 데이터 업데이트
                notifyItemChanged(adapterPosition)

                // ViewModel에 업데이트된 좋아요 정보를 전달
                viewModel.updateLikeStatus(item)
            }

            binding.likeCount.text = "좋아요 ${item.likeCnt}개"
            if(item.commentCnt == 0) {
                binding.commentTxt.visibility = ViewGroup.GONE
            } else {
                binding.commentTxt.text = "댓글 ${item.commentCnt}개 모두 보기"
            }
            binding.timestamp.text = getTimeAgo(item.postingDate)
            binding.executePendingBindings()

            // 쇼츠 클릭 시 상세 페이지로 이동
            binding.shortsImage.setOnClickListener {
                onShortsClick(item.shortsId)
            }
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