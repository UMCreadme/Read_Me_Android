package com.example.readme.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.databinding.ItemChatOtherBinding
import com.example.readme.databinding.ItemChatSelfBinding

class ChatAdapter(private val userId: Int) : ListAdapter<Chat, RecyclerView.ViewHolder>(ChatDiffCallback()) {

    private val ITEM_SELF = 1
    private val ITEM_OTHER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SELF) {
            val binding = ItemChatSelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SelfChatItemViewHolder(binding)
        } else {
            val binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OtherChatItemViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).userId == userId) ITEM_SELF else ITEM_OTHER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = getItem(position)
        if (chat.content.isNullOrBlank()) {
            holder.itemView.visibility = View.GONE
        } else {
            holder.itemView.visibility = View.VISIBLE
            if (holder is SelfChatItemViewHolder) {
                holder.bind(chat)
            } else if (holder is OtherChatItemViewHolder) {
                holder.bind(chat)
            }
        }
    }

    inner class OtherChatItemViewHolder(private val binding: ItemChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.apply {
                otherId.text = chat.nickname
                otherMsg.text = chat.content
                createdAt.text = chat.createdAt.takeIf { it.isNotEmpty() } ?: "시간 없음"
            }
        }
    }

    inner class SelfChatItemViewHolder(private val binding: ItemChatSelfBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.apply {
                selfMsg.text = chat.content
                createdAt.text = chat.createdAt.takeIf { it.isNotEmpty() } ?: "시간 없음"
            }
        }
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}
