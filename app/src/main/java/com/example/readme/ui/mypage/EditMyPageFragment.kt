package com.example.readme.ui.mypage

import MyPageViewModelFactory
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

    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    private val myPageViewModel: MyPageViewModel by viewModels {
        MyPageViewModelFactory(token, apiService)
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
        // MyPageViewModel에서 받아온 값을 EditMyPageViewModel에 설정
        myPageViewModel.fetchMyPage().observe(viewLifecycleOwner) { myPage ->
            if (myPage == null) {
                Log.e("EditMyPageFragment", "No data received from myPageViewModel")
            } else {
                viewModel.setProfileEmail(myPage.result.email)
                viewModel.setProfileName(myPage.result.nickname)
                viewModel.setProfileAccount(myPage.result.account)
                viewModel.setProfileBio(myPage.result.comment ?: "")
                viewModel.setProfileImg(myPage.result.profileImg)
            }
        }
    }

    private fun initAfterBinding() {
        // 백 버튼 클릭 리스너 설정
        binding.backButton.setOnClickListener {
            // 현재 프래그먼트에서 뒤로 가기 처리
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.completeButton.setOnClickListener {
            // EditText에서 변경된 값을 가져와서 ViewModel에 설정
            viewModel.setProfileName(binding.nicknameEditText.text.toString())
            viewModel.setProfileAccount(binding.idEditText.text.toString())
            viewModel.setProfileBio(binding.descriptionEditText.text.toString())
            // 이미지 바인딩 어떻게 할 지
            // viewModel.setProfileImg(binding.profileImage.text.toString())
            saveProfileChanges()
        }

        // 기타 UI 요소들에 대한 이벤트 처리
    }

    private fun saveProfileChanges() {
        lifecycleScope.launch {
            try {
                viewModel.saveProfileChanges(token)
                Log.d("EditMyPageFragment", "updating profile")

            } catch (e: Exception) {
                // 오류 처리
                Log.e("EditMyPageFragment", "Error updating profile", e)
            }
        }
    }
}