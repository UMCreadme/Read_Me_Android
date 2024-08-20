import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.ui.home.Feed.comment.Comment
import kotlinx.coroutines.launch

class CommentViewModel(private val commentListService: CommentListService) : ViewModel() {

    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>> get() = _commentList

    // 서버에서 댓글 리스트 가져오기
    fun fetchCommentList(shortsId: String, page: Int, size: Int) {
        viewModelScope.launch {
            try {
                val response = commentListService.getCommentList(shortsId, page, size)
                if (response.isSuccess) {
                    _commentList.value = response.result
                } else {
                    // 서버 응답이 실패한 경우 처리
                }
            } catch (e: Exception) {
                // 네트워크 오류 처리
            }
        }
    }

    // 서버에 메시지 전송 후, 전체 댓글 리스트 다시 가져오기
    fun addComment(shortsId: String, newComment: Comment) {
        viewModelScope.launch {
            try {
                val postResponse = commentListService.sendMessage(shortsId, newComment)
                if (postResponse.isSuccess) {
                    fetchCommentList(shortsId, 1, 20) // 댓글 전송 후 전체 리스트 다시 가져오기

                } else {
                    // 메시지 전송 실패 처리
                }
            } catch (e: Exception) {
                // 네트워크 오류 처리
            }
        }
    }
}
