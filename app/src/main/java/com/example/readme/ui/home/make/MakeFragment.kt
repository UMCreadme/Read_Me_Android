package com.example.readme.ui.home.make

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.readme.R
import com.example.readme.databinding.FragmentMakeBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.home.make.book.BookSearchFragment
import com.example.readme.ui.home.make.preview.PreViewFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily


class MakeFragment : BaseFragment<FragmentMakeBinding>(R.layout.fragment_make) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var checkedImage: ImageView? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).apply {
            makeShorts()
            binding.tvTitle.text = "프레이즈 만들기"
            binding.btnNext.text = "미리보기"
            checkFormValidity() // 유효성 검사
        }

        setupImagePicker() // 갤러리 접근

        setupCategorySpinner() // 카테고리 스피너
        setupEditTextListeners() // 필수 입력 리스너
        setupDefaultImageClickListener() //
        setupTagInputListener() // 해시태그 입력 리스너 설정

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    Log.d("MakeFragment", "Image selected: $selectedImageUri")
                    addImageToContainer(selectedImageUri)
                }
            }
        }
        // 데이터 수신 설정
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val bookTitle = bundle.getString("bookTitle")
            val ISBN = bundle.getString("ISBN")
            if (bookTitle != null) {
                binding.search.setText(bookTitle)
                binding.ISBN.text = ISBN

            }
        }
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE
        binding.search.setOnClickListener {
            val bottomSheet = BookSearchFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        // EditText를 클릭할 때 리스너가 작동하도록 설정
        binding.search.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val bottomSheet = BookSearchFragment()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        }

        (activity as MainActivity).binding.btnNext.setOnClickListener {
            // 다음 버튼 클릭 시 해쉬 태그 넘기기
            val tags = getTags(binding.etTags.text.toString())
            var result = ""
            tags.forEach { tag ->
                result += "${tag.value} "
            }
            if (result.isBlank()) result = " "
            binding.etTags.setText(result)

            goToPreviewFragment()


        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setupImagePicker() {
        binding.addPhotoButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun addImageToContainer(imageUri: Uri) {
        val inflater = LayoutInflater.from(requireContext())
        val newPhotoContainer = inflater.inflate(R.layout.photo_item_layout, binding.imageContainer, false) as FrameLayout

        val newImageView = newPhotoContainer.findViewById<ShapeableImageView>(R.id.addPhoto)
        newImageView.setImageURI(imageUri)
        newImageView.shapeAppearanceModel = newImageView.shapeAppearanceModel.toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 21f) // 16f는 라운드의 반지름, 필요에 따라 조절
            .build()

        setupImageClickListener(newPhotoContainer)

        binding.imageContainer.addView(newPhotoContainer, 0)


    }



    private fun setupCategorySpinner() {
//        val categories = arrayOf("소설", "시", "에세이", "인문학", "자기계발", "경제/경영",
//                                "정치/사회", "역사", "종교","예술/문화", "과학", "컴퓨터/IT",
//                                "가정/육아", "건강/운동", "취미", "여행", "교육/ 외국어",
//                                "어린이/청소", "만화", "웹소설", "기타")
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }


    // 갤러리에서 업로드한 이미지 클릭 시 체크 표시 및 중복 방지

    // 이미지 클릭 리스너 설정 시, 실제 이미지 뷰를 checkedImage로 저장합니다.
    private fun setupImageClickListener(photoContainer: FrameLayout) {
        val addPhoto = photoContainer.findViewById<ShapeableImageView>(R.id.addPhoto)
        val checkmark = photoContainer.findViewById<ImageView>(R.id.checkmark)

        addPhoto.setOnClickListener {
            if (checkedImage == addPhoto) {
                checkmark.visibility = View.GONE
                checkedImage = null
            } else {
                checkedImage?.let { prevCheckmark ->
                    prevCheckmark.visibility = View.GONE // 이전 체크된 이미지의 체크마크 숨기기
                }
                checkmark.visibility = View.VISIBLE
                checkedImage = addPhoto // checkedImage에 실제 이미지 뷰를 저장
            }
            checkFormValidity()
        }
    }

    // 디폴트 이미지 클릭 리스너 설정 시에도 동일하게 처리합니다.
    private fun setupDefaultImageClickListener() {
        val photoContainer = binding.imageContainer.findViewById<FrameLayout>(R.id.photoContainer)
        val defaultphoto = photoContainer.findViewById<ImageView>(R.id.iv_Default)
        val checkmark = photoContainer.findViewById<ImageView>(R.id.checkmark)

        defaultphoto.setOnClickListener {
            if (checkedImage == defaultphoto) {
                checkmark.visibility = View.GONE
                checkedImage = null
            } else {
                checkedImage?.let { prevCheckmark ->
                    prevCheckmark.visibility = View.GONE
                }
                checkmark.visibility = View.VISIBLE
                checkedImage = defaultphoto // 여기도 실제 이미지 뷰를 저장
            }
            checkFormValidity()
        }
    }

    // goToPreviewFragment에서 이미지 URI를 번들에 추가할 때는, checkedImage에서 URI를 가져옵니다.
    private fun goToPreviewFragment() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        val sentence = binding.etSentence.text.toString()
        val tags = binding.etTags.text.toString()
        val category = binding.tvCategory.text.toString()
        val ISBN = binding.ISBN.text.toString()

        // checkedImage에서 URI 가져오기
        val imageUri = (checkedImage?.tag as? Uri)?.toString()

        val bundle = Bundle().apply {
            putString("title", title)
            putString("content", content)
            putString("sentence", sentence)
            putString("tags", tags)
            putString("category", category)
            putString("imageUri", imageUri)
            Log.d("imageUri", imageUri.toString())
            putString("ISBN", ISBN)
        }

        val previewFragment = PreViewFragment().apply {
            arguments = bundle
        }

        (activity as MainActivity).addFragment(previewFragment)
    }



    // 필수 입력 표시
    private fun setupEditTextListeners() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.titleErrorText.visibility = View.VISIBLE
                } else {
                    binding.titleErrorText.visibility = View.GONE
                }
            }
        })

        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.contentErrorText.visibility = View.VISIBLE
                } else {
                    binding.contentErrorText.visibility = View.GONE
                }
            }
        })

        binding.etSentence.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.sentenceErrorText.visibility = View.VISIBLE
                } else {
                    binding.sentenceErrorText.visibility = View.GONE
                }
            }
        })
    }
    private fun handleTagInput(input: CharSequence) {
        val tags = input.split(" ").filter { it.startsWith("#") }
        if (tags.size > 10) {
            // 초과된 경우 Toast 메시지 표시
            Toast.makeText(requireContext(), "해시태그는 10개까지 가능합니다.", Toast.LENGTH_SHORT).show()
            // 태그가 10개까지만 표시하도록 텍스트 자르기
            val truncatedInput = tags.take(10).joinToString(" ")
            binding.etTags.removeTextChangedListener(tagTextWatcher)
            binding.etTags.setText(truncatedInput)
            binding.etTags.setSelection(truncatedInput.length)
            binding.etTags.addTextChangedListener(tagTextWatcher)
            return
        } else {
            binding.tagsErrorText.visibility = View.GONE
        }

        val spannableStringBuilder = SpannableStringBuilder(input)
        tags.forEach { tag ->
            val start = input.indexOf(tag)
            val end = start + tag.length
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_500)),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // EditText의 내용을 수정하는 방식
        binding.etTags.removeTextChangedListener(tagTextWatcher)
        binding.etTags.text = spannableStringBuilder
        binding.etTags.setSelection(spannableStringBuilder.length)
        binding.etTags.addTextChangedListener(tagTextWatcher)
        checkFormValidity()
    }


    private val tagTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { handleTagInput(it) }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun setupTagInputListener() {
        binding.etTags.addTextChangedListener(tagTextWatcher)
    }

    private fun checkFormValidity() {
        val isTitleValid = binding.etTitle.text.isNotEmpty()
        val isContentValid = binding.etContent.text.isNotEmpty()
        val isSentenceValid = binding.etSentence.text.isNotEmpty()
        val isImageSelected = checkedImage != null

        (activity as MainActivity).binding.btnNext.isEnabled = isTitleValid && isContentValid && isSentenceValid && isImageSelected
        // 필수 선택 문구 가시성 조정
        binding.pictureErrorText.visibility = if (isImageSelected) View.GONE else View.VISIBLE
    }


    // 해쉬태그 추출
    private fun getTags(text: String): Sequence<MatchResult> {
        val pattern = """#([^#\sㄱ-ㅣ가-힣]+)""" // 태그 추출 정규식
        val regex = pattern.toRegex()
        val matches = regex.findAll(text)

        return matches
    }

//    // 번들로 미리보기 화면 이동
//    private fun goToPreviewFragment() {
//
//        val title = binding.etTitle.text.toString()
//        val content = binding.etContent.text.toString()
//        val sentence = binding.etSentence.text.toString()
//        val tags = binding.etTags.text.toString()
//        val category = binding.tvCategory.text.toString()
//        val ISBN = binding.ISBN.text.toString()
//
//        // 이미지 URI를 문자열로 변환
//        val imageUri = (checkedImage?.drawable as? Uri)?.toString()
//
//        val bundle = Bundle().apply {
//            putString("title", title)
//            putString("content", content)
//            putString("sentence", sentence)
//            putString("tags", tags)
//            putString("category", category)
//            putString("imageUri", imageUri)
//            Log.d("imageUri", "${imageUri}")
//            putString("ISBN", ISBN)
//        }
//        val previewFragment = PreViewFragment().apply {
//            arguments = bundle
//        }
//
//        (activity as MainActivity).addFragment(previewFragment)
//    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).apply {
            binding.tvTitle.text = "프레이즈 만들기"
            binding.btnNext.text = "미리보기"
            checkFormValidity() // 유효성 검사
        }
    }
}
