package com.example.readme.ui.home.make

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.databinding.FragmentMakeBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.home.make.book.BookSearchFragment
import com.example.readme.ui.home.make.preview.PreViewFragment
import com.example.whashow.base.BaseFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

class MakeFragment : BaseFragment<FragmentMakeBinding>(R.layout.fragment_make) {

    // 변수 선언
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var imageAdapter: ImageAdapter
    private var checkedImage: ImageView? = null
    private val imageList: MutableList<Uri> = mutableListOf()

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                Log.d("MakeFragment", "Image selected: $selectedImageUri")

                // 이미지 리스트에 새 이미지 추가
                imageList.add(selectedImageUri)
                imageAdapter.notifyItemInserted(imageList.size - 1)

                // 새로 선택된 이미지를 checkedImage에 저장
                checkedImage = binding.rvDefaultPhotos.findViewHolderForAdapterPosition(0)?.itemView?.findViewById(R.id.addPhoto) as? ImageView
                checkedImage?.tag = selectedImageUri


                checkFormValidity()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).apply {
            makeShorts()
            binding.tvTitle.text = "프레이즈 만들기"
            binding.btnNext.text = "미리보기"
            checkFormValidity() // 유효성 검사
        }
        setupRecyclerView()
        setupImagePicker() // 갤러리 접근
        setupCategorySpinner() // 카테고리 스피너
        setupEditTextListeners() // 필수 입력 리스너
        setupTagInputListener() // 해시태그 입력 리스너 설정

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
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


    private fun setupRecyclerView() {
        if (imageList.isEmpty()) {
            val initialImages = listOf(
                Uri.parse("android.resource://com.example.readme/${R.drawable.picture}"),
                Uri.parse("android.resource://com.example.readme/${R.drawable.picture2}"),
                Uri.parse("android.resource://com.example.readme/${R.drawable.picture3}"),
                Uri.parse("android.resource://com.example.readme/${R.drawable.picture4}"),
                Uri.parse("android.resource://com.example.readme/${R.drawable.picture5}")
            )
            imageList.addAll(initialImages)
        }
        imageAdapter = ImageAdapter(imageList).apply {
            onImageClickListener = { imageUri, imageView ->
                checkedImage = if (checkedImage == imageView) {
                    null // 선택된 이미지가 다시 클릭되면 선택 해제
                } else {
                    checkedImage?.let {
                        // 이전 선택된 이미지의 체크마커 숨기기
                        it.findViewById<ImageView>(R.id.checkmark)?.visibility = View.GONE
                    }
                    imageView // 현재 선택된 이미지
                }
                // 선택된 이미지의 체크마커 보이기
                checkedImage?.findViewById<ImageView>(R.id.checkmark)?.visibility = View.VISIBLE
                checkedImage?.tag = imageUri
                checkFormValidity() // 폼 유효성 검사
            }
        }
        binding.rvDefaultPhotos.adapter = imageAdapter
        binding.rvDefaultPhotos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    private fun setupCategorySpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }

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

    private fun setupEditTextListeners() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.titleErrorText.visibility = if (s.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        })

        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.contentErrorText.visibility = if (s.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        })

        binding.etSentence.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.sentenceErrorText.visibility = if (s.isNullOrEmpty()) View.VISIBLE else View.GONE
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

    // 해쉬태그 추출
    private fun getTags(text: String): Sequence<MatchResult> {
        val pattern = """#([^#\sㄱ-ㅣ가-힣]+)""" // 태그 추출 정규식
        val regex = pattern.toRegex()
        val matches = regex.findAll(text)

        return matches
    }

    private fun checkFormValidity() {
        val isTitleValid = binding.etTitle.text.isNotEmpty()
        val isContentValid = binding.etContent.text.isNotEmpty()
        val isSentenceValid = binding.etSentence.text.isNotEmpty()
        val isTagValid = binding.etTags.text.isNotEmpty()
        val isImageSelected = (checkedImage != null)
        Log.d("isImageSelected", "${isImageSelected}")
        binding.pictureErrorText.visibility = if (isImageSelected) View.GONE else View.VISIBLE

        (activity as MainActivity).binding.btnNext.isEnabled = isTitleValid && isContentValid && isSentenceValid && isTagValid && isImageSelected
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).apply {
            binding.tvTitle.text = "프레이즈 만들기"
            binding.btnNext.text = "미리보기"
            checkFormValidity() // 유효성 검사
        }
    }
}
