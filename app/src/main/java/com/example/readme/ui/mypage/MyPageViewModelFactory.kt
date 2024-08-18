import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.mypage.MyPageViewModel

class MyPageViewModelFactory(private val token: String, private val apiService: ReadmeServerService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyPageViewModel(token, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
