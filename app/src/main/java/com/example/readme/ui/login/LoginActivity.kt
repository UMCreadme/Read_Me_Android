package com.example.readme.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.identity.BuildConfig
import com.example.readme.R
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.repository.LoginRepository
import com.example.readme.databinding.ActivityLoginBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.category.CategoryActivity
import com.example.readme.ui.start.StartFragment
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(LoginRepository)
    }
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private var kakaoUser: KaKaoUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("LoginActivity", "onCreate called")

        // 초기에는 StartFragment를 로드하여 보여줍니다.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, StartFragment())
                .commitNow()
            Log.d("LoginActivity", "StartFragment loaded")
        }

        // 카카오 로그인 버튼 클릭 리스너
        binding.kakaoLoginBtn.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    handleLoginResult(token, error)
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
            }
        }

        // 비회원 로그인 버튼 클릭 리스너
        binding.nonMembersTv.setOnClickListener {
            val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean("is_logged_in", false)
                apply()
            }
            nextMainActivity()
        }

        // 회원가입 여부 옵저버 설정
        loginViewModel.isSignedUp.observe(this) {
            if (it == false) {
                kakaoUser?.let { user -> navigateToCategoryActivity(user) }
            }
        }

        // 카카오 사용자 응답 옵저버 설정
        loginViewModel.kakaoUserResponse.observe(this) { response ->
            response?.let {
                val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("is_logged_in", true)
                    apply()
                }
                nextMainActivity()
            }
        }

        // 유저 데이터 옵저버 설정 (디버그 모드에서만 로그 출력)
        loginViewModel.userData.observe(this) { userData ->
            if (BuildConfig.DEBUG) {
                Log.d("LoginActivity", "Observed UserData: $userData")
            }
        }
    }

    // 로그인 결과 처리 함수
    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        when {
            error != null -> {
                Log.e("LoginActivity", "로그인 실패 $error")
            }
            token != null -> {
                Log.d("LoginActivity", "로그인 성공 ${token.accessToken}")
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                getUserInfo()
            }
        }
    }

    // 사용자 정보 요청 함수
    private fun getUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("LoginActivity", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.d("LoginActivity", "사용자 정보 요청 성공: $user")
                kakaoUser = KaKaoUser(id = user.id.toString(), email = user.kakaoAccount?.email ?: "")
                loginViewModel.sendKakaoUserInfo(kakaoUser!!)
            }
        }
    }

    // 메인 액티비티로 이동
    private fun nextMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // 카테고리 액티비티로 이동
    private fun navigateToCategoryActivity(kakaoUser: KaKaoUser) {
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra("uniqueId", kakaoUser.id)
            putExtra("email", kakaoUser.email)
        }
        startActivity(intent)
        finish()
    }

    // StartFragment에서 호출되는 메서드로, StartFragment를 제거하고 로그인 UI를 표시
    fun showLoginUI() {
        Log.d("LoginActivity", "showLoginUI called")
        // StartFragment를 제거
        supportFragmentManager.findFragmentById(R.id.fragment_container)?.let { fragment ->
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitNow()
        }

        // 로그인 UI 요소를 보이게 설정
        binding.readmeLogoIv.visibility = View.VISIBLE
        binding.howtoTv.visibility = View.VISIBLE
        binding.nonMembersTv.visibility = View.VISIBLE
        binding.kakaoLoginBtn.visibility = View.VISIBLE
    }

    // 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        handleLoginResult(token, error)
    }
}
