package com.example.readme.ui.home.Feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.readme.R
import com.example.readme.databinding.FeedItemBinding
import com.example.readme.data.entities.inithome.FeedInfo

class FeedAdapter(var list: ArrayList<FeedInfo>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    // 인터페이스 정의 (아이템 클릭 리스너)
    interface MyItemClickListener {
        fun onItemClick(feed: FeedInfo)
    }

    // 리스너를 설정하는 함수
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    // ViewHolder 정의
    inner class FeedHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val profileImage = binding.feedProfile // 사용자 프로필 이미지
        val username = binding.username         // 사용자 이름
        val shortsImage = binding.shortsImage   // 게시물 이미지
        val content = binding.content           // 게시물 내용
        val likeCount = binding.likeCount       // 좋아요 수
        val commentCount = binding.commentTxt // 댓글 수
        val timestamp = binding.timestamp       // 타임스탬프
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val binding = FeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedHolder(binding)
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val feed = list[position]

        // 프로필 이미지 로드
        Glide.with(holder.itemView.context)
            .load(feed.profileImg)  // API에서 받아온 프로필 이미지 URL
            .into(holder.profileImage)

        holder.username.text = feed.nickname // 사용자 이름

        // 숏츠 이미지 로드
        Glide.with(holder.itemView.context)
            .load(feed.shortsImg)  // API에서 받아온 숏츠 이미지 URL
            .into(holder.shortsImage)

        holder.content.text = feed.content // 게시물 내용
        holder.likeCount.text = "좋아요 ${feed.likeCnt}개" // 좋아요 수
        holder.commentCount.text = "댓글 ${feed.commentCnt}개 모두 보기" // 댓글 수
        holder.timestamp.text = feed.postingDate // 타임스탬프

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener { myItemClickListener.onItemClick(feed) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // 데이터를 업데이트하는 함수
    fun updateData(newList: List<FeedInfo>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if(!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.post_picture_icon)
                    .error(R.drawable.shorts_picture)
                    .override(Target.SIZE_ORIGINAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontTransform())
            .into(view)
    }
}
