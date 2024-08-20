package com.example.readme.ui.community.explore

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.readme.data.entities.CommunityListResponse
import com.example.readme.databinding.ItemCommunityBinding

class CommunityExploreAdapter(
    private val onCommunityClick: (Int) -> Unit
) : ListAdapter<CommunityListResponse, CommunityExploreAdapter.CommunityViewHolder>(
    CommunityInfoDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class CommunityViewHolder(private val binding: ItemCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityListResponse) {
            Glide.with(binding.communityImage.context)
                .load(item.bookImg)
                .transform(CircleCrop())
                .into(binding.communityImage)

            binding.communityTitle.text = item.title
            binding.communityLocation.text = item.location
            binding.itemMembersCurrent.text = item.participants.toString()
            binding.itemMembersTotal.text = item.capacity.toString()

            binding.root.setOnClickListener {
                Log.d("CommunityExploreAdapter", "Item clicked: ${item.communityId}")
                onCommunityClick(item.communityId)
            }
        }

    }
}

class CommunityInfoDiffCallback : DiffUtil.ItemCallback<CommunityListResponse>() {
    override fun areItemsTheSame(oldItem: CommunityListResponse, newItem: CommunityListResponse): Boolean {
        return oldItem.communityId == newItem.communityId
    }

    override fun areContentsTheSame(oldItem: CommunityListResponse, newItem: CommunityListResponse): Boolean {
        return oldItem == newItem
    }

}
