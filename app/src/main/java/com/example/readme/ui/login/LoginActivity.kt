package com.example.readme.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.BuildConfig
import com.example.readme.ui.category.CategoryActivity
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.repository.LoginRepository
import com.example.readme.databinding.ActivityLoginBinding
import com.example.readme.ui.MainActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(LoginRepository)
    }
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private var kakaoUser: KaKaoUser? = null

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("LoginActivity", "로그인 실패 $error")
        } else if (token != null) {
            Log.d("LoginActivity", "로그인 성공 ${token.accessToken}")
            getUserInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    getUserInfo()
                }
            }
        }

        setContentView(binding.root)

        binding.kakaoLoginBtn.setOnClickListener {
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
        binding.nonMembersTv.setOnClickListener {
            nextMainActivity()
        }

        loginViewModel.isSignedUp.observe(this) {
            if (!it) {
                navigateToCategoryActivity(kakaoUser!!)
            }
        }

        // kakaoUserResponse 옵저버 설정
        loginViewModel.kakaoUserResponse.observe(this) { response ->
            if (response != null) {
                nextMainActivity()
            }
        }

        // userData 옵저버 설정
        loginViewModel.userData.observe(this) { userData ->
            Log.d("LoginActivity", "Observed UserData: $userData")
        }
    }

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

    private fun nextMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToCategoryActivity(kakaoUser: KaKaoUser) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("uniqueId", kakaoUser.id)
        intent.putExtra("email", kakaoUser.email)
        startActivity(intent)
        finish()
    }
}
