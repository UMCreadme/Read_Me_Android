package com.example.readme.ui.mypage

import MyPageViewModelFactory
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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
                // 이미지 URI를 MultipartBody.Part로 변환
                val imagePart = prepareFilePart("profileImg", uri)

                // 프로필 이미지 서버에 저장
                updateProfileImg(imagePart)

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

    // prepareFilePart 함수 정의
    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file = uriToFile(fileUri)
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    // uriToFile 함수 정의
    private fun uriToFile(uri: Uri): File {
        val contentResolver = requireContext().contentResolver
        val file = File(requireContext().cacheDir, "temp_profile_image")
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = file.outputStream()

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    // 서버로 프로필 이미지 업로드
    private fun updateProfileImg(imagePart: MultipartBody.Part) {
        lifecycleScope.launch {
            try {
                val token = "example_token" // 여기에 실제 토큰을 사용
                val response = apiService.updateMyProfileImg(token, imagePart)
                if (response.isSuccess) {
                    Log.d("EditMyPageFragment", "Profile image updated successfully")
                    // 업로드 성공 시 완료 토스트 메시지 표시
                    Toast.makeText(requireContext(), "프로필 이미지가 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("EditMyPageFragment", "Profile image update failed")
                    // 업로드 실패 시 실패 토스트 메시지 표시
                    Toast.makeText(requireContext(), "프로필 이미지 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("EditMyPageFragment", "Error updating profile image", e)
                // 예외 발생 시 실패 토스트 메시지 표시
                Toast.makeText(requireContext(), "프로필 이미지 업데이트 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}