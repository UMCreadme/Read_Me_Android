package com.example.readme.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.readme.CategoryActivity
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.databinding.ActivityLoginBinding
import com.example.readme.ui.MainActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.d(TAG, "로그인 성공 ${token.accessToken}")
            getUserInfo()
            nextMainActivity()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.kakaoLoginBtn.id -> {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "로그인 실패 $error")
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                            }
                        } else if (token != null) {
                            Log.d(TAG, "로그인 성공 ${token.accessToken}")
                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                            getUserInfo()
                            nextMainActivity()
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")

        KakaoSdk.init(this, "8bd1ca39d5eb15687ae52deb301f1abe")
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    getUserInfo()
                    nextMainActivity()
                }
            }
        }

        setContentView(binding.root)

        binding.kakaoLoginBtn.setOnClickListener(this)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.kakaoUserResponse.observe(this, Observer{ response ->
            nextMainActivity()
        })
    }
    private  fun getUserInfo(){
        UserApiClient.instance.me { user, error ->
            if (error != null){
                Log.e(TAG, "사용자 정보 요청 실패", error)

            }
            else if (user != null){
                Log.d(TAG,"사용자 정보 요청 성공: $user")
                val kakaoUser = KaKaoUser(id = user.id.toString(), email = user.kakaoAccount?.email ?:"")
                loginViewModel.sendKakaoUserInfo(kakaoUser)
            }
        }
    }


    private fun nextMainActivity() {
        startActivity(Intent(this, UsernameActivity::class.java))
        finish()
    }
}