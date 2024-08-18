package com.example.readme.ui.community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.readme.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val args = arguments
        val isCurrentLocation = args?.getBoolean("isCurrentLocation") ?: false
        if (isCurrentLocation) {
            // 현재 위치 처리 (여기서는 임의의 위치 사용)
            val currentLocation = LatLng(37.5665, 126.978) // 서울
            updateMapView(currentLocation, "현재 위치")
        } else {
            val address = args?.getString("address")
            val latitude = args?.getDouble("latitude")
            val longitude = args?.getDouble("longitude")
            if (latitude != null && longitude != null) {
                val location = LatLng(latitude, longitude)
                updateMapView(location, address)
                sendLocationToServer(latitude, longitude)
            }
        }
    }

    private fun updateMapView(location: LatLng, title: String?) {
        googleMap.addMarker(MarkerOptions().position(location).title(title))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    private fun sendLocationToServer(latitude: Double, longitude: Double) {
        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("latitude", latitude)
            put("longitude", longitude)
        }
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("http://your-server-url/api/saveLocation") // 실제 서버 URL로 변경
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("MapFragment", "Failed to send location", e)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    Log.d("MapFragment", "Location sent successfully")
                } else {
                    Log.e("MapFragment", "Error response: ${response.code}")
                }
            }
        })
    }
}
