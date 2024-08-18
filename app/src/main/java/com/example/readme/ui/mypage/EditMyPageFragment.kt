package com.example.readme.ui.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentEditMypageBinding
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch

class EditMyPageFragment : Fragment(R.layout.fragment_edit_mypage) {

    private lateinit var binding: FragmentEditMypageBinding

    private val token = "example_token" //실제 토큰 저장

    private val viewModel: EditMyPageViewModel by viewModels {
        EditMyPageViewModelFactory(requireActivity().application)
    }

    private val myPageViewModel: MyPageViewModel by activityViewModels()

    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditMypageBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initDataBinding()
        initAfterBinding()
    }

    private fun initDataBinding() {
        // MyPageViewModel에서 받아온 값을 EditMyPageViewModel에 setting
        // MyPage API 수정 후 적용

        myPageViewModel.myPage.observe(viewLifecycleOwner) { it ->
            viewModel.setProfileEmail(it.result.email)
        }
        myPageViewModel.myPage.observe(viewLifecycleOwner) { it ->
            viewModel.setProfileName(it.result.nickname)
        }
        myPageViewModel.myPage.observe(viewLifecycleOwner) { it ->
            viewModel.setProfileAccount("@${it.result.account}")
        }
        myPageViewModel.myPage.observe(viewLifecycleOwner) { it ->
            viewModel.setProfileBio(it.result.comment ?: "")
        }
        myPageViewModel.myPage.observe(viewLifecycleOwner) { it ->
            viewModel.setProfileImg(it.result.profileImg)
        }
    }

    private fun initAfterBinding() {
        binding.backButton.setOnClickListener {
            // 뒤로 가기 처리
            requireActivity().onBackPressed()
        }

        binding.completeButton.setOnClickListener {
            saveProfileChanges()
        }

        // 기타 UI 요소들에 대한 이벤트 처리
    }

    private fun saveProfileChanges() {
        lifecycleScope.launch {
            try {
                // API 호출을 통한 프로필 저장 로직 (수정한 내용을 저장하는 곳!!)
                val profileUpdateRequest = viewModel.getProfileUpdateRequest()

                val response = apiService.updateMyProfile(token, profileUpdateRequest)

                if (response.isSuccess) {
                    Log.d("EditMyPageFragment", "Profile updated successfully")
                    requireActivity().onBackPressed()
                } else {
                    Log.e("EditMyPageFragment", "Error updating profile: ${response.message}")
                }

            } catch (e: Exception) {
                // 오류 처리
                Log.e("EditMyPageFragment", "Error updating profile", e)
            }
        }
    }
}