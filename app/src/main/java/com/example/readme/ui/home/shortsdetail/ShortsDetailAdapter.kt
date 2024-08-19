package com.example.readme.ui.home.shortsdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.R
import com.example.readme.databinding.ShortsdetailItemBinding
import com.example.readme.data.entities.detail.ShortsDetailInfo
import com.example.readme.ui.home.make.preview.FlowLayout

class ShortsDetailAdapter(var list: ArrayList<ShortsDetailInfo>) : RecyclerView.Adapter<ShortsDetailAdapter.ShortsDetailHolder>() {

    // 인터페이스 정의 (아이템 클릭 리스너)
    interface MyItemClickListener {
        fun onItemClick(shortsDetail: ShortsDetailInfo)
        fun onImageClick(shortsDetail: ShortsDetailInfo)
        fun onLikeClick(shortsDetail:ShortsDetailInfo, isLiked: Boolean)
    }

    // 리스너를 설정하는 함수
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class ShortsDetailHolder(val binding: ShortsdetailItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

        // 아이콘 업데이트
        private fun updateLikeIcon() {
            if (isLiked) {
                binding.likeIcon.visibility = View.GONE
                binding.likefillIcon.visibility = View.VISIBLE
            } else {
                binding.likeIcon.visibility = View.VISIBLE
                binding.likefillIcon.visibility = View.GONE
            }
        }

        fun bind(shortsDetail: ShortsDetailInfo) {
            // 초기 상태 설정
            isLiked = shortsDetail.isLike
            updateLikeIcon()

            // 기존 데이터 바인딩 로직
            Glide.with(binding.root.context)
                .load(shortsDetail.profileImg)
                .into(binding.feedProfile)

            binding.username.text = shortsDetail.userAccount

            Glide.with(binding.root.context)
                .load(shortsDetail.shortsImg)
                .into(binding.shortsImage)

            binding.feedTitle.text = shortsDetail.title
            binding.feedContent.text = shortsDetail.content
            binding.likeCount.text = shortsDetail.likeCnt.toString()
            binding.commentCount.text = shortsDetail.commentCnt.toString()

            adjustViewPosition(binding.feedSentence, shortsDetail.phraseX, shortsDetail.phraseY)

            addTagsToFlowLayout(binding.etTags, shortsDetail.tags)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsDetailHolder {
        val binding = ShortsdetailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShortsDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsDetailHolder, position: Int) {
        val shortsDetail = list[position]
        holder.bind(shortsDetail)  // bind 함수를 호출하여 데이터를 바인딩
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // 데이터를 업데이트하는 함수
    fun updateData(newList: List<ShortsDetailInfo>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    private fun adjustViewPosition(view: View, x: Double, y: Double) {
        view.x = x.toFloat()
        view.y = y.toFloat()
    }

    private fun addTagsToFlowLayout(layout: FlowLayout, tags: List<String>) {
        layout.removeAllViews()  // 기존 태그 제거

        layout.horizontalSpacing = 16
        layout.verticalSpacing = 16

        for (tag in tags) {
            val tagTextView = LayoutInflater.from(layout.context).inflate(R.layout.tag_item, layout, false) as TextView
            tagTextView.text = tag
            layout.addView(tagTextView)
        }
    }
}
