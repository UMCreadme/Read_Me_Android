package com.example.readme.ui.home.Feed

import CommentFragment
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.readme.databinding.FeedItemBinding
import com.example.readme.data.entities.inithome.FeedInfo

import java.text.SimpleDateFormat
import java.util.*

class FeedAdapter(var list: ArrayList<FeedInfo>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    init {
        setHasStableIds(true)
    }


    // 인터페이스 정의 (아이템 클릭 리스너)
    interface MyItemClickListener {
        fun onItemClick(feed: FeedInfo)
        fun onImageClick(feed: FeedInfo)
        fun onLikeClick(feed: FeedInfo, isLiked: Boolean)
//        fun onProfileClick(feed: FeedInfo) TODO: 프로필 연결
    }

    // 리스너를 설정하는 함수
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    // ViewHolder 정의
    inner class FeedHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isLiked = false

        init {
            binding.likeIcon.setOnClickListener {
                toggleLikeState()
            }

            binding.likefillIcon.setOnClickListener {
                toggleLikeState()
            }
        }

        // 좋아요 상태 토글
        private fun toggleLikeState() {
            isLiked = !isLiked
            updateLikeIcon()
            myItemClickListener.onLikeClick(list[adapterPosition], isLiked)


        }

        // 좋아요 업데이트
        private fun updateLikeIcon() {
            if (isLiked) {
                binding.likeIcon.visibility = View.GONE
                binding.likefillIcon.visibility = View.VISIBLE
            } else {
                binding.likeIcon.visibility = View.VISIBLE
                binding.likefillIcon.visibility = View.GONE
            }
        }

        fun bind(feed: FeedInfo) {

            isLiked = feed.isLike
            updateLikeIcon()


            Glide.with(binding.root.context)
                .load(feed.profileImg)
                .centerInside()
                .into(binding.feedProfile)

            binding.username.text = feed.nickname

            Glide.with(binding.root.context)
                .load(feed.shortsImg)
                .centerInside()
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.shortsImage.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Called when the resource is no longer needed, here you can clear the background if needed
                    }
                })

            binding.mainTitle.text = feed.title
            binding.content.text = feed.content
            binding.likeCount.text = "좋아요 ${feed.likeCnt}개"
            binding.commentTxt.text = "댓글 ${feed.commentCnt}개 모두 보기"
            binding.timestamp.text = calculateDaysAgo(feed.postingDate)


            adjustViewPosition(binding.feedSentence, feed.phraseX, feed.phraseY)

            binding.shortsImage.setOnClickListener {
                myItemClickListener.onImageClick(feed)  // 이미지 클릭 시 호출
            }

            binding.commentIcon.setOnClickListener {
                // FragmentActivity를 통해 FragmentManager를 가져옴
                val fragmentActivity = itemView.context as? FragmentActivity
                fragmentActivity?.let {
                    val commentFragment = CommentFragment()
                    commentFragment.show(it.supportFragmentManager, "CommentFragment")
                }
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

    override fun getItemId(position: Int): Long {
        return list[position].shortsId.hashCode().toLong() // 각 아이템의 고유 ID 반환
    }

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

