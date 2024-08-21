import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.readme.R
import com.example.readme.databinding.CommentItemBinding
import com.example.readme.ui.home.Feed.comment.Comment

class CommentAdapter (private val recyclerView: RecyclerView) :RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var comments = emptyList<Comment>()

    inner class CommentViewHolder(private val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            Log.d("CommentAdapter", "nickname: ${comment.account}")
            binding.usernameTv.text = comment.account // username_tv 대신 usernameTv로 변경
            binding.commentTextTv.text = comment.content // comment_text_tv 대신 commentTextTv로 변경
            binding.commentDate.text = comment.passedDate // comment_date로 변경

            Glide.with(binding.userProfileIv.context)
                .load(comment.profileImg) // 이미지 URL
                .placeholder(R.drawable.userprofile_default) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지 (옵션)
                .error(R.drawable.userprofile_default)
                .transform(CircleCrop())
                .into(binding.userProfileIv) // ImageView에 이미지 설정
            // 프로필 이미지 및 다른 UI 요소들 바인딩 추가
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

    fun submitList(newComments: List<Comment>) {
        comments = newComments
        notifyDataSetChanged() // 전체 리스트가 변경된 경우 호출
        recyclerView.scrollToPosition(comments.size - 1)

    }
}
