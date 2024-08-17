package com.example.readme.ui.community

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        // Intent로부터 정보 받아오기
        val address = intent.getStringExtra("extra_road_addr")
        val latitude = intent.getDoubleExtra("extra_latitude", 0.0)
        val longitude = intent.getDoubleExtra("extra_longitude", 0.0)

        // 주소 정보를 TextView에 표시
        findViewById<TextView>(R.id.addressTextView).text = address

        // MapView 초기화
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 전달된 위치에 마커를 추가하고, 지도 이동
        val location = LatLng(intent.getDoubleExtra("extra_latitude", 0.0), intent.getDoubleExtra("extra_longitude", 0.0))
        googleMap.addMarker(MarkerOptions().position(location).title(intent.getStringExtra("extra_road_addr")))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f)) // 줌 레벨 설정
    }

    // MapView 생명주기 메소드
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
