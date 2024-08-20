package com.example.readme.ui.home.Feed.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.data.remote.Comment
import com.example.readme.databinding.CommentItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var comments = emptyList<Comment>()

    inner class CommentViewHolder(private val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.commentTextTv.text = comment.content
            binding.commentDate.text = getFormattedTimeDifference(comment.passedDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    // 리스트 전체를 교체하고 notifyDataSetChanged() 호출
    fun submitList(newComments: List<Comment>) {
        comments = newComments
        notifyDataSetChanged() // 전체 리스트가 변경된 경우 호출
    }

    private fun getFormattedTimeDifference(passedDate: String): String {
        val now = Date()

        // passedDate를 Date로 변환 (예: "yyyy-MM-dd HH:mm:ss" 포맷 사용)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val time: Date = format.parse(passedDate) ?: return "날짜 형식 오류"

        val diffInMillis = now.time - time.time

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return when {
            minutes < 1 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            days < 7 -> "${days}일 전"
            else -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(time)
        }
    }
}
