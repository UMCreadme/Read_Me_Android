package com.example.readme.ui.home.make.preview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import com.example.readme.R
import com.example.readme.databinding.FragmentPreViewBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.data.entities.ShortsPostResponse
import com.example.readme.ui.home.main.HomeFragment
import com.example.readme.ui.home.make.MakeFragment
import com.example.readme.ui.utils.RetrofitClient
import com.example.whashow.base.BaseFragment
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class PreViewFragment : BaseFragment<FragmentPreViewBinding>(R.layout.fragment_pre_view) {

    private var phraseX: Float = 0.0f
    private var phraseY: Float = 0.0f

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
            val ISBN = bundle.getString("ISBN")

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
            val imageUriString = arguments?.getString("imageUri")
            handleImageUpload(imageUriString)

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
                MotionEvent.ACTION_UP -> {
                    // ACTION_UP 이벤트 시 최종 위치를 저장
                    phraseX = v.x
                    phraseY = v.y
                    return@OnTouchListener true
                }
                else -> return@OnTouchListener false
            }
        })
    }
    private fun handleImageUpload(imageUriString: String?) {
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            when {
                imageUri.scheme == "content" -> {
                    // 갤러리에서 가져온 이미지
                    val imageFile = File(getRealPathFromURI(imageUri))
                    uploadGalleryImage(imageFile)
                }
                imageUri.scheme == "android.resource" -> {
                    // drawable에 있는 이미지
                    val resourceId = imageUri.lastPathSegment?.toIntOrNull()
                    resourceId?.let {
                        uploadDrawableImage(it)
                    }
                }
                else -> {
                    // 다른 URI 처리
                    Log.e("ImageUpload", "알 수 없는 이미지 소스")
                }
            }
        } else {
            Log.e("ImageUpload", "이미지 URI가 null입니다.")
        }
    }

    // 갤러리 이미지의 실제 경로를 가져오는 메서드
    private fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        val cursor = context?.contentResolver?.query(uri, null, null, null, null)
        cursor?.let {
            it.moveToFirst()
            val idx = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = it.getString(idx)
            it.close()
        }
        return path
    }

    // 갤러리 이미지 업로드
    private fun uploadGalleryImage(imageFile: File) {
        val title = binding.feedTitle.text.toString()
        val content = binding.feedContent.text.toString()
        val sentence = binding.feedSentence.text.toString()
        val tags = binding.etTags.children.toList().map { (it as TextView).text.toString() }
        val ISBN = arguments?.getString("ISBN") ?: ""
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

        val requestBodyMap = mutableMapOf<String, RequestBody>()
        // 여기에 추가적인 request body를 넣을 수 있습니다.
        requestBodyMap["ISBN"] = RequestBody.create("text/plain".toMediaTypeOrNull(), ISBN)
        requestBodyMap["phrase"] = RequestBody.create("text/plain".toMediaTypeOrNull(), sentence)
        requestBodyMap["shortsTitle"] = RequestBody.create("text/plain".toMediaTypeOrNull(), title)
        requestBodyMap["content"] = RequestBody.create("text/plain".toMediaTypeOrNull(), content)
        // 태그를 여러 개의 파라미터로 추가
        tags.forEachIndexed { index, tag ->
            requestBodyMap["tags[$index]"] = RequestBody.create("text/plain".toMediaTypeOrNull(), tag)
        }
        requestBodyMap["phraseX"] = RequestBody.create("text/plain".toMediaTypeOrNull(), phraseX.toString())
        requestBodyMap["phraseY"] = RequestBody.create("text/plain".toMediaTypeOrNull(), phraseY.toString())


        val service = RetrofitClient.getShortsPostService()
        val call = service.uploadShorts(requestBodyMap, imagePart)

        call.enqueue(object : Callback<ShortsPostResponse> {
            override fun onResponse(call: Call<ShortsPostResponse>, response: Response<ShortsPostResponse>) {
                Log.d("upload 갤러리", "${response.body()}")
                if (response.body()?.isSuccess == true) {
                    Toast.makeText(context, "업로드 성공", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).addFragment(HomeFragment())
                } else {
                    Toast.makeText(context, "업로드 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ShortsPostResponse>, t: Throwable) {
                Toast.makeText(context, "서버 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Drawable 이미지 업로드
    private fun uploadDrawableImage(resourceId: Int) {
        val title = binding.feedTitle.text.toString()
        val content = binding.feedContent.text.toString()
        val sentence = binding.feedSentence.text.toString()
        val tags = binding.etTags.children.toList().map { (it as TextView).text.toString() }
        val ISBN = arguments?.getString("ISBN") ?: ""
        val drawable = context?.resources?.getDrawable(resourceId, null)
        val bitmap = (drawable as BitmapDrawable).bitmap


        // Bitmap을 ByteArrayOutputStream으로 변환
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
        val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

        val requestBodyMap = mutableMapOf<String, RequestBody>()

        requestBodyMap["ISBN"] = RequestBody.create("text/plain".toMediaTypeOrNull(), ISBN)
        requestBodyMap["phrase"] = RequestBody.create("text/plain".toMediaTypeOrNull(), sentence)
        requestBodyMap["shortsTitle"] = RequestBody.create("text/plain".toMediaTypeOrNull(), title)
        requestBodyMap["content"] = RequestBody.create("text/plain".toMediaTypeOrNull(), content)
        // 태그를 여러 개의 파라미터로 추가
        tags.forEachIndexed { index, tag ->
            requestBodyMap["tags[$index]"] = RequestBody.create("text/plain".toMediaTypeOrNull(), tag)
        }
        requestBodyMap["phraseX"] = RequestBody.create("text/plain".toMediaTypeOrNull(), phraseX.toString())
        requestBodyMap["phraseY"] = RequestBody.create("text/plain".toMediaTypeOrNull(), phraseY.toString())
        Log.d("requestBodyMap", "${ISBN}, ${sentence}, ${title}, ${content}, ${tags}, ${phraseX}, ${phraseY} ")

        val service = RetrofitClient.getShortsPostService()
        val call = service.uploadShorts(requestBodyMap, imagePart)

        call.enqueue(object : Callback<ShortsPostResponse> {
            override fun onResponse(call: Call<ShortsPostResponse>, response: Response<ShortsPostResponse>) {
                Log.d("upload", "${response.body()}")
                if (response.body()?.isSuccess == true) {
                    Toast.makeText(context, "업로드 성공", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).addFragment(HomeFragment())
                } else {
                    Toast.makeText(context, "업로드 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ShortsPostResponse>, t: Throwable) {
                Toast.makeText(context, "서버 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




//    private fun uploadPreviewData() {
//        val title = binding.feedTitle.text.toString()
//        val content = binding.feedContent.text.toString()
//        val sentence = binding.feedSentence.text.toString()
//        val tags = binding.etTags.children.toList().map { (it as TextView).text.toString() }.joinToString(" ")
//        val ISBN = arguments?.getString("ISBN") ?: ""
//        val imageUriString = arguments?.getString("imageUri")
//        val imageFile = imageUriString?.let { Uri.parse(it).path?.let { it1 -> File(it1) } }
//
//        val requestFile = imageFile?.let {
//            RequestBody.create("image/jpeg".toMediaTypeOrNull(), it)
//        }
//        val imagePart = requestFile?.let {
//            MultipartBody.Part.createFormData("image", imageFile.name, it)
//        }
//
//        val requestBodyMap = mutableMapOf<String, RequestBody>()
//
//        requestBodyMap["ISBN"] = RequestBody.create("text/plain".toMediaTypeOrNull(), ISBN)
//        requestBodyMap["phrase"] = RequestBody.create("text/plain".toMediaTypeOrNull(), sentence)
//        requestBodyMap["shortsTitle"] = RequestBody.create("text/plain".toMediaTypeOrNull(), title)
//        requestBodyMap["content"] = RequestBody.create("text/plain".toMediaTypeOrNull(), content)
//        requestBodyMap["tags"] = RequestBody.create("text/plain".toMediaTypeOrNull(), tags)
//        requestBodyMap["phraseX"] = RequestBody.create("text/plain".toMediaTypeOrNull(), phraseX.toString())
//        requestBodyMap["phraseY"] = RequestBody.create("text/plain".toMediaTypeOrNull(), phraseY.toString())
//
//        val service = RetrofitClient.getShortsPostService()
//
//        val call = service.uploadShorts(
//            body = requestBodyMap,
//            image = imagePart
//        )
//
//        call.enqueue(object : Callback<ShortsPostResponse> {
//            override fun onResponse(call: Call<ShortsPostResponse>, response: Response<ShortsPostResponse>) {
//                Log.d("upload", "${response.body()}")
//                if (response.body()?.isSuccess == true) {
//                    Toast.makeText(context, "업로드 성공", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "업로드 실패", Toast.LENGTH_SHORT).show()
//                    Log.e("Upload Error", "Error Code: ${response.code()}")
//
//                }
//            }
//
//            override fun onFailure(call: Call<ShortsPostResponse>, t: Throwable) {
//                Toast.makeText(context, "서버 오류: ${t.message}", Toast.LENGTH_SHORT).show()
//                Log.e("Upload Failure", "Throwable: ${t.message}")
//            }
//        })
//    }
}
