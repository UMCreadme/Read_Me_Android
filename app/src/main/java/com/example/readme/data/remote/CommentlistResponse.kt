import com.example.readme.ui.home.Feed.comment.Comment

data class CommentListResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val pageInfo: PageInfo,
    val result: List<Comment>
)

data class PageInfo(
    val page: Int,
    val size: Int,
    val hasNext: Boolean
)
