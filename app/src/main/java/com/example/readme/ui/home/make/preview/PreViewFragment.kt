package com.example.readme.ui.home.make.preview

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import com.example.readme.R
import com.example.readme.databinding.FragmentPreViewBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class PreViewFragment : BaseFragment<FragmentPreViewBinding>(R.layout.fragment_pre_view) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).makeShorts()
        (activity as MainActivity).binding.tvTitle.text = "프레이즈 미리 보기"
        (activity as MainActivity).binding.btnNext.text = "업로드"

        // arguments로 전달된 데이터 추출 및 UI 반영
        arguments?.let { bundle ->
            val title = bundle.getString("title")
            val content = bundle.getString("content")
            val sentence = bundle.getString("sentence")
            val tags = bundle.getString("tags")
            val imageUriString = bundle.getString("imageUri")
//            val imageUri = imageUriString?.let { Uri.parse(it) }
            val ISBN = bundle.getString("ISBN")

            // 데이터 사용
            binding.feedTitle.text = title
            binding.feedContent.text = content
            binding.feedSentence.text = sentence
            if (imageUriString != null) {
                binding.shortsImage.setImageURI(Uri.parse(imageUriString))
            }
            else{
                Log.d("image", "실패")
            }

            // 사용자가 sentence를 드래그하여 위치를 변경할 수 있도록 설정
            enableDragAndDrop(binding.feedSentence)

            // 태그를 분리하여 FlowLayout에 추가
            val tagList = tags?.split(" ")?.filter { it.startsWith("#") } ?: emptyList()
            addTagsToFlowLayout(tagList)
        }

        binding.etTags.horizontalSpacing = 16
        binding.etTags.verticalSpacing = 16

        (activity as MainActivity).binding.btnNext.setOnClickListener {
//            uploadPreviewData()
        }


    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun initAfterBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).resetToolbar()
    }

    private fun addTagsToFlowLayout(tags: List<String>) {
        binding.etTags.removeAllViews()  // 기존 태그 제거

        for (tag in tags) {
            val tagTextView = LayoutInflater.from(requireContext()).inflate(R.layout.tag_item, binding.etTags, false) as TextView
            tagTextView.text = tag
            binding.etTags.addView(tagTextView)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableDragAndDrop(view: TextView) {
        var dX = 0f
        var dY = 0f

        view.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = v.x - event.rawX
                    dY = v.y - event.rawY
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    v.animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                    return@OnTouchListener true
                }
                else -> return@OnTouchListener false
            }
        })
    }

//    private fun uploadPreviewData() {
//        val isbn = RequestBody.create(MultipartBody.FORM, "isbn_value") // ISBN 값
//        val title = RequestBody.create(MultipartBody.FORM, binding.feedTitle.text.toString())
//        val cid = RequestBody.create(MultipartBody.FORM, "cid_value") // CID 값
//        val bookCover = RequestBody.create(MultipartBody.FORM, "bookCover_url") // 책 표지 URL
//        val author = RequestBody.create(MultipartBody.FORM, "author_name") // 저자 이름
//        val link = RequestBody.create(MultipartBody.FORM, "purchase_link") // 구매 링크
//        val phrase = RequestBody.create(MultipartBody.FORM, binding.feedSentence.text.toString())
//        val postTitle = RequestBody.create(MultipartBody.FORM, binding.feedTitle.text.toString())
//        val content = RequestBody.create(MultipartBody.FORM, binding.feedContent.text.toString())
//
//        // 태그 리스트
//        val tagsList = mutableListOf<RequestBody>()
//        val tags = binding.etTags.children.map { it as TextView }.map { it.text.toString() }
//        for (tag in tags) {
//            tagsList.add(RequestBody.create(MultipartBody.FORM, tag))
//        }
//
//        // 문구의 위치와 크기 정보
//        val xPosition = RequestBody.create(MultipartBody.FORM, binding.feedSentence.x.toString())
//        val yPosition = RequestBody.create(MultipartBody.FORM, binding.feedSentence.y.toString())
//
//        // 이미지 URI를 MultipartBody.Part로 변환
//        val imageUriString = arguments?.getString("imageUri")
//        val imageUri = imageUriString?.let { Uri.parse(it) }
//
//        // imageUri가 null이 아닌지 확인하고 MultipartBody.Part로 변환
//        val imagePart = imageUri?.let { uri ->
//            val imageFile = File(uri.path)
//            val requestFile = RequestBody.create(MultipartBody.FORM, imageFile)
//            MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
//        }
//
//        // API 호출을 통해 데이터를 업로드
//        val call = RetrofitClient.makeShortsService.uploadPreview(
//            isbn = isbn,
//            title = title,
//            cid = cid,
//            bookCover = bookCover,
//            author = author,
//            link = link,
//            phrase = phrase,
//            postTitle = postTitle,
//            content = content,
//            tags = tagsList,
//            xPosition = xPosition,
//            yPosition = yPosition,
//            image = imagePart!!
//        )
//
//        // 서버 응답 처리
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(context, "업로드 성공!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "업로드 실패: ${response.code()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(context, "업로드 실패: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }


}
