package com.example.readme.ui.home.shortsdetail

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.readme.R
import com.example.readme.databinding.ShortsdetailItemBinding
import com.example.readme.data.entities.detail.ShortsDetailInfo
import com.example.readme.data.entities.inithome.FeedInfo
import com.example.readme.ui.home.Feed.FeedAdapter.FeedDiffCallback
import com.example.readme.ui.home.make.preview.FlowLayout

class ShortsDetailAdapter(private val viewModel: ShortsDetailViewModel, var list: ArrayList<ShortsDetailInfo>) : RecyclerView.Adapter<ShortsDetailAdapter.ShortsDetailHolder>() {

    init {
        setHasStableIds(true)
    }

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


        fun bind(shortsDetail: ShortsDetailInfo) {

            if(shortsDetail.isLike) {
                binding.likeIcon.setImageResource(R.drawable.likefill_icon)
            } else {
                binding.likeIcon.setImageResource(R.drawable.like_icon)
            }

            binding.likeIcon.setOnClickListener {
                // 좋아요 상태를 업데이트
                shortsDetail.isLike = !shortsDetail.isLike
                shortsDetail.likeCnt += if (shortsDetail.isLike) 1 else -1

                // 어댑터의 데이터 업데이트
                notifyItemChanged(adapterPosition)

                // ViewModel에 업데이트된 좋아요 정보를 전달
                viewModel.updateLikeStatus(shortsDetail)
            }

            binding.likeCount.text = "${shortsDetail.likeCnt}"
            // 기존 데이터 바인딩 로직
            Glide.with(binding.root.context)
                .load(shortsDetail.profileImg)
                .into(binding.feedProfile)

            binding.username.text = shortsDetail.userAccount

            // 쇼츠 정보 세팅 (이미지 & 구절)
            Glide.with(binding.root.context)
                .load(shortsDetail.shortsImg)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        binding.shortsImage.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Called when the resource is no longer needed, here you can clear the background if needed
                    }
                })
            binding.feedSentence.text = shortsDetail.phrase

            binding.mainTitle.text = shortsDetail.title
            binding.content.text = shortsDetail.content
            binding.commentCount.text = shortsDetail.commentCnt.toString()
            adjustViewPosition(binding.feedSentence, shortsDetail.phraseX, shortsDetail.phraseY)
            binding.feedSentence.text = shortsDetail.phrase
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

    override fun getItemId(position: Int): Long {
        return list[position].shortsId.hashCode().toLong() // 각 아이템의 고유 ID 반환
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // 데이터를 업데이트하는 함수
    fun updateData(newList: List<ShortsDetailInfo>) {
        val diffCallback = ShortsDetailDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
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

    class ShortsDetailDiffCallback(
        private val oldList: List<ShortsDetailInfo>,
        private val newList: List<ShortsDetailInfo>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].shortsId == newList[newItemPosition].shortsId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
