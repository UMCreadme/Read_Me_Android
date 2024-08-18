package com.example.readme.ui.community

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.community.data.remote.GeocodingResponse
import com.example.readme.data.remote.GeocodingService
import com.example.readme.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookAppointActivity : AppCompatActivity() {

    private lateinit var searchPlaceEditText: EditText
    private lateinit var searchButton: ImageView
    private lateinit var locationRecyclerView: RecyclerView
    private lateinit var geocodingService: GeocodingService
    private val apiKey = "AIzaSyAnr87XpMn-jRGdCtk6HbxNf3OezfHcKgI"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appoint)

        // 뷰 초기화
        searchPlaceEditText = findViewById(R.id.search_place)
        searchButton = findViewById(R.id.btn_current_location)
        locationRecyclerView = findViewById(R.id.locationRecyclerView)
        locationRecyclerView.layoutManager = LinearLayoutManager(this)

        // Retrofit 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        geocodingService = retrofit.create(GeocodingService::class.java)

        searchButton.setOnClickListener {
            val address = searchPlaceEditText.text.toString()
            searchLocation(address)
        }

        // 현재 위치 버튼 클릭 시 MapFragment로 이동
        val currentLocationButton = findViewById<ImageView>(R.id.btn_current_location)
        currentLocationButton.setOnClickListener {
            openMapFragment(true)
        }
    }

    private fun searchLocation(address: String) {
        geocodingService.getCoordinates(address, apiKey).enqueue(object : Callback<GeocodingResponse> {
            @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
                if (response.isSuccessful) {
                    val locations = response.body()?.results ?: emptyList()
                    locationRecyclerView.adapter = LocationAdapter(locations) { selectedLocation ->
                        openMapFragment(false,
                            selectedLocation.formatted_address,
                            selectedLocation.geometry.location.latitude,
                            selectedLocation.geometry.location.longitude)
                    }
                } else {
                    Toast.makeText(this@BookAppointActivity, "주소 검색에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                Toast.makeText(this@BookAppointActivity, "주소 검색에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openMapFragment(isCurrentLocation: Boolean, address: String? = null, latitude: Double? = null, longitude: Double? = null) {
        val fragment = MapFragment().apply {
            arguments = Bundle().apply {
                putBoolean("isCurrentLocation", isCurrentLocation)
                putString("address", address)
                putDouble("latitude", latitude ?: 0.0)
                putDouble("longitude", longitude ?: 0.0)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
