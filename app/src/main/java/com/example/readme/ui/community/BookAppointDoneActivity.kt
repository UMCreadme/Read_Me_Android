package com.example.readme.ui.community

import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class BookAppointDoneActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appoint_done)

        // 전달된 데이터를 받아옴
        val address = intent.getStringExtra("extra_road_addr") ?: ""
        val latitude = intent.getDoubleExtra("extra_latitude", 0.0)
        val longitude = intent.getDoubleExtra("extra_longitude", 0.0)

        // TextView에 주소 표시
        val placeTextView = findViewById<TextView>(R.id.place)
        placeTextView.text = address

        val place2TextView = findViewById<TextView>(R.id.place2)
        place2TextView.text = address // 두 번째 장소도 동일한 주소를 표시하도록 설정

        // Spinners에 기본 값 설정 (예: 현재 날짜와 시간)
        val monthSpinner = findViewById<Spinner>(R.id.month_spinner)
        val daySpinner = findViewById<Spinner>(R.id.day_spinner)
        val ampmSpinner = findViewById<Spinner>(R.id.ampm_spinner)
        val hourSpinner = findViewById<Spinner>(R.id.hour_spinner)

        // 필요한 경우 데이터에 맞춰 Spinner의 값을 설정 (예: 특정 날짜와 시간)
        // monthSpinner.setSelection(...)

        // Google Map 초기화
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // 전달된 위도와 경도로 마커 추가
        val location = LatLng(intent.getDoubleExtra("extra_latitude", 0.0), intent.getDoubleExtra("extra_longitude", 0.0))
        googleMap.addMarker(MarkerOptions().position(location).title("만남 장소"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f)) // 줌 레벨 설정
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
