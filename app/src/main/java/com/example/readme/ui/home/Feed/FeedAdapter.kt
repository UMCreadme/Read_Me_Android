package com.example.readme.ui.home.Feed

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.readme.R
import com.example.readme.data.entities.inithome.FeedInfo
import com.example.readme.databinding.FeedItemBinding
import java.text.SimpleDateFormat
import java.util.*

class FeedAdapter(private val viewModel: FeedViewModel, var list: ArrayList<FeedInfo>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    init {
        setHasStableIds(true)
    }

    // 인터페이스 정의 (아이템 클릭 리스너)
    interface MyItemClickListener {
        fun onItemClick(feed: FeedInfo)
        fun onImageClick(feed: FeedInfo)
        fun onLikeClick(feed: FeedInfo, isLiked: Boolean)
    }

    // 리스너를 설정하는 함수
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    // ViewHolder 정의
    inner class FeedHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: FeedInfo) {
            // 유저 정보 세팅
            Glide.with(binding.root.context)
                .load(feed.profileImg)
                .circleCrop()
                .into(binding.feedProfile)
            binding.username.text = feed.nickname

            // 쇼츠 정보 세팅 (이미지 & 구절)
            Glide.with(binding.root.context)
                .load(feed.shortsImg)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        binding.shortsImage.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Called when the resource is no longer needed, here you can clear the background if needed
                    }
                })
            binding.feedSentence.text = feed.phrase

            // 좋아요 버튼
            if(feed.isLike) {
                binding.likeIcon.setImageResource(R.drawable.likefill_icon)
            } else {
                binding.likeIcon.setImageResource(R.drawable.like_icon)
            }

            binding.likeIcon.setOnClickListener {
                // 좋아요 상태를 업데이트
                feed.isLike = !feed.isLike
                feed.likeCnt += if (feed.isLike) 1 else -1

                // 어댑터의 데이터 업데이트
                notifyItemChanged(adapterPosition)

                // ViewModel에 업데이트된 좋아요 정보를 전달
                viewModel.updateLikeStatus(feed)
            }

            binding.likeCount.text = "좋아요 ${feed.likeCnt}개"
            binding.mainTitle.text = feed.title
            binding.content.text = feed.content
            if(feed.commentCnt == 0) {
                binding.commentTxt.visibility = ViewGroup.GONE
            } else {
                binding.commentTxt.text = "댓글 ${feed.commentCnt}개 모두 보기"
            }
            binding.timestamp.text = calculateDaysAgo(feed.postingDate)
            binding.executePendingBindings()

            adjustViewPosition(binding.feedSentence, feed.phraseX, feed.phraseY)

            binding.shortsImage.setOnClickListener {
                myItemClickListener.onImageClick(feed)  // 이미지 클릭 시 호출
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val binding = FeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val feed = list[position]
        holder.bind(feed)

    }

//    override fun getItemId(position: Int): Long {
//        return list[position].shortsId.hashCode().toLong() // 각 아이템의 고유 ID 반환
//    }

    private fun adjustViewPosition(view: View, x: Double, y: Double) {
        view.x = x.toFloat()
        view.y = y.toFloat()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // 데이터를 업데이트하는 함수
    fun updateData(newList: List<FeedInfo>) {
        val diffCallback = FeedDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDaysAgo(postingDate: String): String {
        val nowDateTime = Calendar.getInstance().timeInMillis
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREAN)
        val postingDateTime = df.parse(postingDate)?.time ?: return ""

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

    class FeedDiffCallback(
        private val oldList: List<FeedInfo>,
        private val newList: List<FeedInfo>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].userId == newList[newItemPosition].userId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

