package com.example.readme.ui.community

import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.example.readme.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAppointActivity : AppCompatActivity() {

    private lateinit var searchButton: ImageView
    private lateinit var webView: WebView
    private lateinit var confirmButton: Button
    private lateinit var placeStringEditText: EditText
    private lateinit var placeStringEditText2: EditText
    private lateinit var handler: Handler

    private lateinit var monthSpinner: Spinner
    private lateinit var daySpinner: Spinner
    private lateinit var ampmSpinner: Spinner
    private lateinit var hourSpinner: Spinner

    private var selectedAddress: String? = null
    private var selectedBuilding: String? = null
    private var selectedLatitude: Double? = null
    private var selectedLongitude: Double? = null

    private val TAG = "BookAppointActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appoint)

        Log.d(TAG, "onCreate: Activity started")

        // 뷰 초기화
        searchButton = findViewById(R.id.btn_current_location)
        confirmButton = findViewById(R.id.confirm_button)
        placeStringEditText = findViewById(R.id.place_string_edit)
        placeStringEditText2 = findViewById(R.id.place_string_edit2)

        monthSpinner = findViewById(R.id.month_spinner)
        daySpinner = findViewById(R.id.day_spinner)
        ampmSpinner = findViewById(R.id.ampm_spinner)
        hourSpinner = findViewById(R.id.hour_spinner)

        handler = Handler()

        // WebView 초기화
        initWebView()

        // 리스너 추가
        addListeners()

        // 현재 위치 버튼 클릭 시 MapActivity로 이동
        searchButton.setOnClickListener {
            Log.d(TAG, "onCreate: Search button clicked")
            val intent = Intent(this, MapMarkerActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_MAP)
        }

        // 확인 버튼 클릭 시 동작 처리
        confirmButton.setOnClickListener {
            if (areAllFieldsValid()) {
                val intent = Intent(this, MyChatFragment::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 전달받은 주소 설정
        val intent = intent
        selectedAddress = intent.getStringExtra("selected_address")
        if (!selectedAddress.isNullOrEmpty()) {
            Log.d(TAG, "onCreate: Received address - $selectedAddress")
            placeStringEditText.setText(selectedAddress)
        }
    }

    private fun initWebView() {
        Log.d(TAG, "initWebView: Initializing WebView")

        webView = findViewById(R.id.location_search_webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true

        webView.addJavascriptInterface(AndroidBridge(), "Android")
        Log.d(TAG, "initWebView: Added JavaScript interface")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "onPageFinished: Page Loaded - $url")
                webView.loadUrl("javascript:sample2_execDaumPostcode();")
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                Log.e(TAG, "onReceivedSslError: SSL Error received - ${error?.primaryError}")
                handler?.proceed()
            }
        }

        Log.d(TAG, "initWebView: Loading Kakao postcode service")
        webView.loadUrl("https://seulseul-35d52.web.app")
    }

    private fun addListeners() {
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                checkFieldsAndToggleButton()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        monthSpinner.onItemSelectedListener = spinnerListener
        daySpinner.onItemSelectedListener = spinnerListener
        ampmSpinner.onItemSelectedListener = spinnerListener
        hourSpinner.onItemSelectedListener = spinnerListener

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndToggleButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        placeStringEditText.addTextChangedListener(textWatcher)
        placeStringEditText2.addTextChangedListener(textWatcher)
    }

    private fun areAllFieldsValid(): Boolean {
        val isAddressFilled = placeStringEditText.text.toString().isNotEmpty()
        val isBuildingFilled = placeStringEditText2.text.toString().isNotEmpty()
        val isMonthSelected = monthSpinner.selectedItemPosition != 0
        val isDaySelected = daySpinner.selectedItemPosition != 0
        val isAmpmSelected = ampmSpinner.selectedItemPosition != 0
        val isHourSelected = hourSpinner.selectedItemPosition != 0

        return isAddressFilled && isBuildingFilled && isMonthSelected && isDaySelected && isAmpmSelected && isHourSelected
    }

    private fun checkFieldsAndToggleButton() {
        confirmButton.isEnabled = areAllFieldsValid()
    }

    private inner class AndroidBridge {
        @JavascriptInterface
        fun processDATA(roadAddress: String, buildingName: String) {
            handler.post {
                selectedAddress = roadAddress
                selectedBuilding = buildingName

                Log.d(TAG, "processAddress: Address received - $selectedAddress, building: $selectedBuilding")

                placeStringEditText.requestFocus()
                placeStringEditText.setText(selectedAddress)

                Toast.makeText(this@BookAppointActivity, "주소가 선택되었습니다: $selectedAddress", Toast.LENGTH_SHORT).show()

                sendLocationToServer(selectedAddress ?: "", 0.0, 0.0)
            }
        }
    }

    private fun sendLocationToServer(address: String, latitude: Double, longitude: Double) {
        Log.d(TAG, "sendLocationToServer: Sending location to server - Address: $address, Latitude: $latitude, Longitude: $longitude")

        val locationService = RetrofitClient.getLocationService()
        val locationData = LocationData(address, latitude, longitude)

        locationService.sendLocation(locationData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "sendLocationToServer: Location sent successfully")
                    Toast.makeText(this@BookAppointActivity, "위치 정보 전송 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendLocationToServer: Failed to send location - ${response.errorBody()}")
                    Toast.makeText(this@BookAppointActivity, "위치 정보 전송 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "sendLocationToServer: Error - ${t.message}")
                Toast.makeText(this@BookAppointActivity, "서버와의 연결 실패: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val REQUEST_CODE_MAP = 1001
    }
}
