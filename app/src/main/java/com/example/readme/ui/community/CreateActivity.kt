package com.example.readme.ui.community

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.readme.R

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
        setContentView(R.layout.activity_create)  // Ensure the correct layout file name

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
        tagsEditText.addTextChangedListener(textWatcher)
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
            // 데이터 검증 및 전송
            if (validateFields()) {
                // 화면 전환
                val intent = Intent(this, CreateFragmentActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
