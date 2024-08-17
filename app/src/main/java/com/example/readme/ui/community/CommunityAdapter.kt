package com.example.readme.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.databinding.ItemCommunityBinding

class CommunityAdapter(private val communityList: List<Community>) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bind(communityList[position])
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    inner class CommunityViewHolder(private val binding: ItemCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(communityItem: Community) {
            binding.itemImage.setImageResource(communityItem.imageResId)
            binding.itemLocation.text = communityItem.location
            binding.itemTitle.text = communityItem.title
            binding.itemMembersCurrent.text = communityItem.currentMembers.toString()
            binding.itemMembersTotal.text = communityItem.totalMembers.toString()

            // 태그 설정
            binding.tagBox1.text = communityItem.tags.getOrNull(0) ?: ""
            binding.tagBox2.text = communityItem.tags.getOrNull(1) ?: ""
        }
    }
}
