package com.example.readme.ui.community

import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapMarkerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // SupportMapFragment를 가져오고 지도가 사용할 준비가 되었을 때 알림을 받습니다.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 지도에 초기 위치 설정 (예: 홍익대학교)
        val seoul = LatLng(37.552635722509, 126.92436042413)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15f)) // Zoom level adjusted to 15f

        // 확대/축소 컨트롤 활성화
        mMap.uiSettings.isZoomControlsEnabled = true

        // 지도 클릭 리스너 설정
        mMap.setOnMapClickListener { latLng ->
            currentMarker?.remove()  // 기존 마커 제거
            val marker = mMap.addMarker(MarkerOptions().position(latLng).title("모임 장소"))
            currentMarker = marker  // 현재 마커 업데이트
            marker?.showInfoWindow()
        }

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val geocoder = Geocoder(this, Locale.KOREA)
        val addresses = geocoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)
        val latitude = marker.position.latitude
        val longitude = marker.position.longitude
        val coordsMessage = "좌표: $latitude, $longitude"

        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            val city = address.adminArea ?: "알 수 없는 지역"  // 주 또는 시
            val district = address.subLocality ?: "알 수 없는 구"  // 구 또는 동
            val message = "$city $district 로 설정하시겠습니까?\n$coordsMessage"

            // 다이얼로그 표시
            AlertDialog.Builder(this)
                .setTitle("위치 설정")
                .setMessage(message)
                .setPositiveButton("확인") { dialog, which ->
                    // 확인 버튼 클릭 시 동작
                }
                .setNegativeButton("닫기") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        } else {
            // 주소를 찾을 수 없을 때 좌표값만 표시
            AlertDialog.Builder(this)
                .setTitle("위치 설정")
                .setMessage(coordsMessage)
                .setPositiveButton("확인") { dialog, which ->
                    // 확인 버튼 클릭 시 동작
                }
                .setNegativeButton("닫기") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
        return true
    }
}
