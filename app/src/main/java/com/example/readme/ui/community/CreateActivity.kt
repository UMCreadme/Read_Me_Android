package com.example.readme.ui.community

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.readme.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class CreateActivity : AppCompatActivity() {

    private lateinit var bookImageView: ImageView
    private lateinit var bookTitleTextView: TextView
    private lateinit var bookAuthorTextView: TextView
    private lateinit var bookClubNameEditText: EditText
    private lateinit var tagsEditText: EditText
    private lateinit var participantsEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var placeTextViews: List<TextView>
    private var selectedPlace: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // 뷰 초기화
        bookImageView = findViewById(R.id.bookImageView)
        bookTitleTextView = findViewById(R.id.bookTitleTextView)
        bookAuthorTextView = findViewById(R.id.bookAuthorTextView)
        bookClubNameEditText = findViewById(R.id.bookClubNameEditText)
        tagsEditText = findViewById(R.id.tagsEditText)
        participantsEditText = findViewById(R.id.participantsEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        updateButton = findViewById(R.id.updateButton)

        // 장소 텍스트 뷰 초기화
        placeTextViews = listOf(
            findViewById(R.id.place_seoul),
            findViewById(R.id.place_busan),
            findViewById(R.id.place_daegu),
            findViewById(R.id.place_incheon),
            findViewById(R.id.place_gwangju),
            findViewById(R.id.place_daejeon),
            findViewById(R.id.place_ulsan),
            findViewById(R.id.place_sejong),
            findViewById(R.id.place_gyeonggi),
            findViewById(R.id.place_gangwon),
            findViewById(R.id.place_chungbuk),
            findViewById(R.id.place_chungnam),
            findViewById(R.id.place_jeonbuk),
            findViewById(R.id.place_jeonnam),
            findViewById(R.id.place_gyeongbuk),
            findViewById(R.id.place_gyeongnam),
            findViewById(R.id.place_jeju)
        )

        // 리스너 설정
        descriptionEditText.addTextChangedListener(textWatcher)
        tagsEditText.addTextChangedListener(tagsTextWatcher)
        participantsEditText.addTextChangedListener(textWatcher)
        placeTextViews.forEach { placeTextView ->
            placeTextView.setOnClickListener {
                selectedPlace = placeTextView.text.toString()
                updateSelectedPlaceUI(placeTextView)
                validateFields()
            }
        }

        // 초기 버튼 상태 설정
        validateFields()

        // 업데이트 버튼 클릭 리스너
        updateButton.setOnClickListener {
            if (validateFields()) {
                val tagsList = getTagsList()
                postTagsToServer(tagsList)

                val intent = Intent(this, CreateFragmentActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // 태그 리스트를 분리하여 반환
    private fun getTagsList(): List<String> {
        val tagsText = tagsEditText.text.toString().trim()
        return tagsText.split("#").filter { it.isNotEmpty() } // 빈 문자열 필터링
    }

    // 태그를 서버로 POST 요청으로 전송
    private fun postTagsToServer(tagsList: List<String>) {
        val client = OkHttpClient()
        val url = "https://your-server-endpoint.com/api/tags" // 서버 URL을 여기에 입력

        // JSON 배열로 태그 리스트 변환
        val jsonArray = JSONArray(tagsList)
        val jsonObject = JSONObject().apply {
            put("tags", jsonArray)
        }

        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            jsonObject.toString()
        )

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CreateActivity, "서버 요청 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CreateActivity, "태그가 성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@CreateActivity, "서버 오류: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    // 일반 TextWatcher
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    // 태그 TextWatcher
    private val tagsTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val tags = s.toString().trim()
            val tagList = tags.split("#").filter { it.isNotEmpty() }

            // 태그의 개수와 각 태그의 길이를 체크
            if (tagList.size > 10 || tagList.any { it.length > 10 }) {
                tagsEditText.removeTextChangedListener(this)
                tagsEditText.setText(tags.substring(0, start))
                tagsEditText.setSelection(start)
                tagsEditText.addTextChangedListener(this)

                // 토스트 메시지 표시
                Toast.makeText(this@CreateActivity, "글자 수는 10개, 태그의 개수는 10개로 제한합니다.", Toast.LENGTH_SHORT).show()
            }
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateFields(): Boolean {
        val description = descriptionEditText.text.toString().trim()
        val tags = tagsEditText.text.toString().trim()
        val participantsText = participantsEditText.text.toString().trim()
        val participants = participantsText.toIntOrNull()

        val isDescriptionValid = description.length <= 300
        val isTagsValid = tags.split("#").all { it.length <= 10 } && tags.split("#").size <= 10
        val isParticipantsValid = participants != null && participants in 4..10
        val isPlaceSelected = selectedPlace != null

        val isFormValid = isDescriptionValid && isTagsValid && isParticipantsValid && isPlaceSelected

        updateButton.isEnabled = isFormValid
        updateButton.setBackgroundColor(
            if (isFormValid) ContextCompat.getColor(this, R.color.Primary)
            else ContextCompat.getColor(this, R.color.Light_Gray)
        )

        return isFormValid
    }

    private fun updateSelectedPlaceUI(selectedTextView: TextView) {
        placeTextViews.forEach { placeTextView ->
            placeTextView.setBackgroundColor(
                if (placeTextView == selectedTextView) {
                    ContextCompat.getColor(this, R.color.Primary)
                } else {
                    ContextCompat.getColor(this, R.color.Light_Gray)
                }
            )
        }
    }
}
