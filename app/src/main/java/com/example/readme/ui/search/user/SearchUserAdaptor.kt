package com.example.readme.ui.search.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.entities.SearchUserResult
import com.example.readme.databinding.ItemSearchUserBinding

class SearchUserAdaptor : ListAdapter<SearchUserResult, SearchUserAdaptor.UserViewHolder>(
    UserInfoDiffCallback()
) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val binding = ItemSearchUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val item = getItem(position)
            if (item != null) {
                holder.bind(item)
            }
        }

        inner class UserViewHolder(private val binding: ItemSearchUserBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: SearchUserResult) {
                binding.user = item
                binding.executePendingBindings()
            }
        }
}

class UserInfoDiffCallback : DiffUtil.ItemCallback<SearchUserResult>() {
    override fun areItemsTheSame(oldItem: SearchUserResult, newItem: SearchUserResult): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: SearchUserResult, newItem: SearchUserResult): Boolean {
        return oldItem == newItem
    }
}