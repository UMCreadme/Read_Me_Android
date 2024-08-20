package com.example.readme.ui.mypage

import MyPageViewModelFactory
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.readme.R
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentEditMypageBinding
import com.example.readme.ui.MainActivity
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch

class EditMyPageFragment : Fragment(R.layout.fragment_edit_mypage) {

    private lateinit var binding: FragmentEditMypageBinding

    private val token = "example_token"

    private val viewModel: EditMyPageViewModel by viewModels {
        EditMyPageViewModelFactory(requireActivity().application)
    }

    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    private val myPageViewModel: MyPageViewModel by viewModels {
        MyPageViewModelFactory(token, apiService)
    }

    // 이미지 선택 결과를 처리하기 위한 ActivityResultLauncher 설정
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                // 이미지 URI를 사용해 프로필 사진 업데이트
                binding.profileImage.setImageURI(uri)


                // URI가 잘 저장되었는지 LiveData를 observe하여 확인
                viewModel.profileImg.observe(viewLifecycleOwner) { imgUri ->
                    Log.d("ViewModel uri", imgUri ?: "No URI set")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditMypageBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        (activity as MainActivity).NoShow() //툴바 안보이게
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

                // 프로필 이미지를 Glide를 사용하여 ImageView에 로드
                Glide.with(this)
                    .load(myPage.result.profileImg)
                    .transform(CircleCrop())
                    .into(binding.profileImage)
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
            viewModel.setProfileImg(binding.profileImage.toString())

            saveProfileChanges()

            // 이전 화면으로 돌아가기
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // 기타 UI 요소들에 대한 이벤트 처리

        // 사진 수정 버튼 클릭 리스너
        binding.btnPicEdit.setOnClickListener {
            openGallery()
        }

        binding.btnEditDefaultPic.setOnClickListener{
            // 기본 사진 바인딩으로 바꾸기
        }

        // 닉네임 EditText 내용 지우기
        binding.btnEditNickname.setOnClickListener {
            binding.nicknameEditText.text.clear()
        }

        // ID EditText 내용 지우기
        binding.btnEditId.setOnClickListener {
            binding.idEditText.text.clear()
        }

        // 설명 EditText 내용 지우기
        binding.btnEditBio.setOnClickListener {
            binding.descriptionEditText.text.clear()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
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