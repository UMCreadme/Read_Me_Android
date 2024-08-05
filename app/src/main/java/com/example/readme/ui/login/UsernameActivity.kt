package com.example.readme.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.CategoryActivity
import com.example.readme.databinding.ActivityUsernameBinding
import com.kakao.sdk.user.UserApiClient

class UsernameActivity :AppCompatActivity(){
    private val binding by lazy { ActivityUsernameBinding.inflate(layoutInflater) }

override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    setContentView(binding.root)

    binding.usernameNextBtn.setOnClickListener {
        nextMainActivity()
    }
//    UserApiClient.instance.me { user, error ->
//        if (error != null) {
//            Log.e(TAG, "사용자 정보 요청 실패 $error")
//        } else if (user != null) {
//            Log.d(TAG, "사용자 정보 요청 성공 : $user")
//            binding.txtNickName.text = user.kakaoAccount?.profile?.nickname
//            binding.txtAge.text = user.kakaoAccount?.ageRange.toString()
//            binding.txtEmail.text = user.kakaoAccount?.email
//        }
//    }
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e(TAG, "사용자 정보 요청 실패", error)
        } else if (user != null) {
            Log.d(TAG, "사용자 정보 요청 성공: $user")

            val kakaoid = user.id
            val nickname = user.kakaoAccount?.profile?.nickname
            val ageRange = user.kakaoAccount?.ageRange
            val email = user.kakaoAccount?.email


            Log.d(TAG, "회원번호:${user.id}")
            Log.d(TAG, "닉네임: $nickname")
            Log.d(TAG, "나이: $ageRange")
            Log.d(TAG, "이메일: $email")

            binding.txtKakaoId.text = kakaoid.toString()
            binding.txtNickName.text = nickname
            binding.txtAge.text = ageRange.toString()
            binding.txtEmail.text = email

            Log.i(TAG, "사용자 정보 요청 성공" +
                    "\n회원번호: ${user.id}" +
                    "\n이메일: ${user.kakaoAccount?.email}" +
                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"+
                    "\n나이 : ${user.kakaoAccount?.ageRange}")
            val scopes = mutableListOf<String>()

            if (user.kakaoAccount?.emailNeedsAgreement == true) {
                scopes.add("account_email")
            }
            if (user.kakaoAccount?.birthdayNeedsAgreement == true) {
                scopes.add("birthday")
            }
            if (user.kakaoAccount?.birthyearNeedsAgreement == true) {
                scopes.add("birthyear")
            }
            if (user.kakaoAccount?.genderNeedsAgreement == true) {
                scopes.add("gender")
            }
            if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) {
                scopes.add("phone_number")
            }
            if (user.kakaoAccount?.profileNeedsAgreement == true) {
                scopes.add("profile")
            }
            if (user.kakaoAccount?.ageRangeNeedsAgreement == true) {
                scopes.add("age_range")
            }
            if (user.kakaoAccount?.ciNeedsAgreement == true) {
                scopes.add("account_ci")
            }

            if (scopes.isNotEmpty()) {
                Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다. 동의 항목: $scopes")
                UserApiClient.instance.loginWithNewScopes(this, scopes) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "추가 동의 요청 실패", error)
                    } else {
                        Log.d(TAG, "추가 동의 요청 성공: $token")
                        // 추가 동의를 받은 후 사용자 정보 재요청
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.d(TAG, "사용자 정보 요청 성공: $user")
                                val nickname = user.kakaoAccount?.profile?.nickname
                                val ageRange = user.kakaoAccount?.ageRange
                                val email = user.kakaoAccount?.email

                                Log.d(TAG, "닉네임: $nickname")
                                Log.d(TAG, "나이: $ageRange")
                                Log.d(TAG, "이메일: $email")

                                binding.txtNickName.text = nickname
                                binding.txtAge.text = ageRange.toString()
                                binding.txtEmail.text = email
                            }
                        }
                    }
                }
            }
        }
    }
}
    private fun nextMainActivity() {
        startActivity(Intent(this, CategoryActivity::class.java))
        finish()
    }
}