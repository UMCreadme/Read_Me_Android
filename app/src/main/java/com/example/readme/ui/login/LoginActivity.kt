package com.example.readme.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.identity.BuildConfig
import com.example.readme.ui.category.CategoryActivity
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.repository.LoginRepository
import com.example.readme.databinding.ActivityLoginBinding
import com.example.readme.ui.MainActivity
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

    // 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        handleLoginResult(token, error)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kakao SDK 초기화는 Application 클래스에서 진행
        setContentView(binding.root)

        // 로그인 상태 확인 후 토큰이 있으면 사용자 정보 요청
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    getUserInfo()
                }
            }
        }

        // 카카오 로그인 버튼 클릭 리스너
        binding.kakaoLoginBtn.setOnClickListener {
            // 로그인 방법 선택
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {

                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("LoginActivity", "로그인 실패 $error")
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        } else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                        }
                    } else if (token != null) {
                        Log.d("LoginActivity", "로그인 성공 ${token.accessToken}")
//                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        getUserInfo()
                    }
                }

            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
            }
        }

        // 비회원 로그인 버튼 클릭 리스너
        binding.nonMembersTv.setOnClickListener {
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
}
