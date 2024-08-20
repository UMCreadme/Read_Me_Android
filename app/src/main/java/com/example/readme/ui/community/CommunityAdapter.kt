package com.example.readme.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.entities.CommunityItem

class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    private var items: List<CommunityItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<CommunityItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.item_title)
        private val location: TextView = itemView.findViewById(R.id.item_location)
        private val membersCurrent: TextView = itemView.findViewById(R.id.item_members_current)
        private val membersTotal: TextView = itemView.findViewById(R.id.item_members_total)

        fun bind(item: CommunityItem) {
            title.text = item.title
            location.text = item.location
            membersCurrent.text = item.membersCurrent.toString()
            membersTotal.text = item.membersTotal.toString()
        }
    }
}
