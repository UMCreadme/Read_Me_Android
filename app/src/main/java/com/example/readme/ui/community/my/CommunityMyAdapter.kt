package com.example.readme.ui.community.my

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.readme.R
import com.example.readme.data.entities.MyCommunityListResponse
import com.example.readme.databinding.ItemCommunityMyBinding
import com.example.readme.ui.home.make.preview.FlowLayout

class CommunityMyAdapter(
    private val onCommunityClick: (Int) -> Unit
) : ListAdapter<MyCommunityListResponse, CommunityMyAdapter.CommunityMyViewHolder>(
    MyCommunityInfoDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityMyViewHolder {
        val binding = ItemCommunityMyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityMyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityMyViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class CommunityMyViewHolder(private val binding: ItemCommunityMyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyCommunityListResponse) {
            Glide.with(binding.communityImage.context)
                .load(item.bookImg)
                .transform(CircleCrop())
                .into(binding.communityImage)

            binding.communityTitle.text = item.title
            binding.communityLocation.text = item.location
            binding.itemMembersCurrent.text = item.participants.toString()
            binding.itemMembersTotal.text = item.capacity.toString()
            binding.unreadMessageCount.text = "${item.unReadCount}개"

            binding.chatRoomItem.setOnClickListener {
                onCommunityClick(item.communityId)
            }

            addTagsToFlowLayout(binding.communityTags, item.tags)
        }
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

class MyCommunityInfoDiffCallback : DiffUtil.ItemCallback<MyCommunityListResponse>() {
    override fun areItemsTheSame(oldItem: MyCommunityListResponse, newItem: MyCommunityListResponse): Boolean {
        return oldItem.communityId == newItem.communityId
    }

    override fun areContentsTheSame(oldItem: MyCommunityListResponse, newItem: MyCommunityListResponse): Boolean {
        return oldItem == newItem
    }
}