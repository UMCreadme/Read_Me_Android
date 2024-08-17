package com.example.readme.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.databinding.ShortsdetailItemBinding
import com.bumptech.glide.Glide // 또는 다른 이미지 로딩 라이브러리 사용
import com.example.readme.data.entities.detail.ShortsDetailInfo

class ShortsDetailAdapter(val list: List<ShortsDetailInfo>) : RecyclerView.Adapter<ShortsDetailAdapter.ShortsDetailHolder>() {

    inner class ShortsDetailHolder(private val binding: ShortsdetailItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        // ViewBinding을 통해 아이템 뷰를 참조합니다.
        val commentCount = binding.commentCount
        val likeCount = binding.likeCount
        val postImage = binding.shortsImage
        val postProfile = binding.feedProfile
        val postContent = binding.feedContent
        val username = binding.username
        val postTitle = binding.feedTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsDetailHolder {
        val binding = ShortsdetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortsDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsDetailHolder, position: Int) {
        val shortsDetail = list[position]

        // Glide를 사용하여 이미지 로딩
        Glide.with(holder.itemView.context)
            .load(shortsDetail.profileImg) // 사용자 프로필 이미지 URL
            .into(holder.postProfile)

        Glide.with(holder.itemView.context)
            .load(shortsDetail.shortsImg) // 쇼츠 이미지 URL
            .into(holder.postImage)

        holder.username.text = shortsDetail.userAccount // 사용자 이름
        holder.postContent.text = shortsDetail.content // 게시물 내용
        holder.likeCount.text = shortsDetail.likeCnt.toString() // 좋아요 수
        holder.postTitle.text = shortsDetail.title // 게시물 제목
        holder.commentCount.text = shortsDetail.commentCnt.toString() // 댓글 수
    }

    override fun getItemCount(): Int = list.size
}
