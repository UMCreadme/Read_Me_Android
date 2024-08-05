import androidx.lifecycle.MutableLiveData
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.ui.login.KakaoLoginService
import com.example.readme.ui.login.KakaoResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginRepository {
    companion object {
        private const val TAG = "LoginRepository"
        private const val BASE_URL = "https://dev.umcreadme11.shop/"

        private lateinit var kakaoLoginService: KakaoLoginService
        private var kakaoLoginMutableLiveData: MutableLiveData<KakaoResponse> = MutableLiveData()

        init {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val gson: Gson = GsonBuilder().setLenient().create()

            kakaoLoginService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(KakaoLoginService::class.java)
        }

        suspend fun sendKakaoUserInfo(user: KaKaoUser): KakaoResponse {
            return kakaoLoginService.sendKakaoUserInfo(user)
        }
    }
}
