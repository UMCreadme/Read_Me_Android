package com.example.readme.ui.community

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R

class BookAppointActivity : AppCompatActivity() {

    private lateinit var selectedPlaceTextView: TextView
    private lateinit var specificPlaceSection: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appoint)

        // 뷰 초기화
        val placeSeoul: TextView = findViewById(R.id.place_seoul)
        val placeBusan: TextView = findViewById(R.id.place_busan)
        val placeDaegu: TextView = findViewById(R.id.place_daegu)
        val placeIncheon: TextView = findViewById(R.id.place_incheon)
        val placeGwangju: TextView = findViewById(R.id.place_gwangju)
        val placeDaejeon: TextView = findViewById(R.id.place_daejeon)
        val placeUlsan: TextView = findViewById(R.id.place_ulsan)
        val placeSejong: TextView = findViewById(R.id.place_sejong)
        val placeGyeonggi: TextView = findViewById(R.id.place_gyeonggi)
        val placeGangwon: TextView = findViewById(R.id.place_gangwon)
        val placeChungbuk: TextView = findViewById(R.id.place_chungbuk)
        val placeChungnam: TextView = findViewById(R.id.place_chungnam)
        val placeJeonbuk: TextView = findViewById(R.id.place_jeonbuk)
        val placeJeonnam: TextView = findViewById(R.id.place_jeonnam)
        val placeGyeongbuk: TextView = findViewById(R.id.place_gyeongbuk)
        val placeGyeongnam: TextView = findViewById(R.id.place_gyeongnam)
        val placeJeju: TextView = findViewById(R.id.place_jeju)
        val placeCafeMaru: TextView = findViewById(R.id.place_cafemaru)
        val placeStudyCafe: TextView = findViewById(R.id.place_study_cafe)
        val placeStudyRoom: TextView = findViewById(R.id.place_study_room)
        val confirmButton: Button = findViewById(R.id.confirm_button)
        specificPlaceSection = findViewById(R.id.specific_place_section)

        val places = listOf(
            placeSeoul, placeBusan, placeDaegu, placeIncheon, placeGwangju, placeDaejeon, placeUlsan,
            placeSejong, placeGyeonggi, placeGangwon, placeChungbuk, placeChungnam, placeJeonbuk,
            placeJeonnam, placeGyeongbuk, placeGyeongnam, placeJeju
        )

        // 장소 TextView에 클릭 리스너 추가
        places.forEach { place ->
            place.setOnClickListener {
                selectPlace(place)
            }
        }

        confirmButton.setOnClickListener {
            // 약속 설정 동작 처리
            Toast.makeText(this, "약속이 설정되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectPlace(selectedPlace: TextView) {
        if (::selectedPlaceTextView.isInitialized) {
            // 이전에 선택된 장소 초기화
            selectedPlaceTextView.setBackgroundResource(R.drawable.location_background)
            selectedPlaceTextView.setTextColor(Color.BLACK)
        }

        // 선택된 장소 하이라이트
        selectedPlaceTextView = selectedPlace
        selectedPlaceTextView.setBackgroundResource(R.drawable.location_selected_background)
        selectedPlaceTextView.setTextColor(Color.WHITE)

        // 세부 장소 섹션 표시
        specificPlaceSection.visibility = View.VISIBLE
    }
}