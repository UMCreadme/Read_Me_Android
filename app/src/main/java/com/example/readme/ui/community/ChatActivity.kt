package com.example.readme.ui.community

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.entities.ChatMessage
import com.example.readme.data.remote.CommunityMessageResponse
import com.example.readme.data.remote.MessageRequest
import com.example.readme.utils.RetrofitClient
import com.google.android.material.navigation.NavigationView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mapView: MapView  // MapView 추가
    private val communityId = "50"  // 테스트 커뮤니티 ID
    private val userId = 134  // 테스트용 사용자 ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // MapView 초기화
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        // 지도를 비동기로 가져오고 초기화
        mapView.getMapAsync { googleMap ->
            val seoul = LatLng(37.5611, 126.9976)
            googleMap.addMarker(MarkerOptions().position(seoul).title("Hongik"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15f))
        }

        // 다른 뷰 초기화
        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navButton: ImageButton = findViewById(R.id.nav_button)

        // RecyclerView 설정
        chatAdapter = ChatAdapter(userId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(userId)
        recyclerView.adapter = chatAdapter

        // DrawerLayout 및 ActionBarDrawerToggle 설정
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 내비게이션 버튼을 클릭했을 때 드로어 열기/닫기
        navButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        // 내비게이션 항목 선택 처리
        navView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                // 내비게이션 메뉴 항목 클릭 처리
            }
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }

        // 기존 메시지 가져오기
        fetchMessages()

        // 메시지 전송 버튼 클릭 리스너
        sendButton.setOnClickListener {
            val messageContent = messageInput.text.toString()
            if (messageContent.isNotEmpty()) {
                sendMessage(messageContent)
            }
        }
    }

    // CommunityMessageResponse를 ChatMessage로 변환하는 확장 함수
    private fun CommunityMessageResponse.toChatMessage(userId: Int, nickname: String): ChatMessage? {
        // 유효하지 않은 데이터가 들어왔을 경우 기본값 설정
        val id = if (this.id > 0) this.id else 0
        val content = this.content ?: "message"
        val timestamp = this.timestamp ?: "Unknown time"

        return ChatMessage(
            messageId = id,
            userId = userId,
            nickname = nickname, // 실제 닉네임으로 대체할 수 있음
            content = content,
            createdAt = timestamp,
            isMine = false // 기본값, 필요에 따라 조정 가능
        )
    }

    // 메시지를 가져오는 함수
    private fun fetchMessages() {
        val service = RetrofitClient.getChatFetchService()

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    service.fetchMessages(communityId)
                }

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("ChatActivity", "Response body: $body")

                    if (body?.isSuccess == true) {
                        val messages = body.result?.mapNotNull {
                            it.toChatMessage(userId, "unknown") // 실제 닉네임으로 대체할 수 있음
                        } ?: emptyList()

                        if (messages.isNotEmpty()) {
                            chatAdapter.submitList(messages)
                            Log.d("ChatActivity", "Messages: $messages")
                            recyclerView.visibility = View.VISIBLE
                        } else {
                            recyclerView.visibility = View.GONE
                        }
                    } else {
                        showToast("메시지 로드 실패: ${body?.message}")
                    }
                } else {
                    showToast("메시지 로드 실패: ${response.code()}")
                }
            } catch (e: IOException) {
                showToast("네트워크 오류, 나중에 다시 시도해 주세요.")
            } catch (e: HttpException) {
                showToast("메시지 로드 실패: ${e.code()}")
            }
        }
    }

    // 메시지를 전송하는 함수
    private fun sendMessage(content: String) {
        val service = RetrofitClient.getChatFetchService()
        val messageRequest = MessageRequest(content)

        lifecycleScope.launch {
            try {
                val response = service.sendMessage(communityId, messageRequest)
                if (response.isSuccessful) {
                    // 전송 성공 시 메시지를 RecyclerView에 추가
                    val chatMessage = ChatMessage(
                        messageId = 0,  // 서버에서 반환된 ID로 대체
                        userId = userId,
                        nickname = "Me",  // 실제 닉네임으로 대체
                        content = content,
                        createdAt = "Just now",
                        isMine = true
                    )

                    // 현재 메시지 리스트에 새 메시지 추가
                    val currentMessages = chatAdapter.currentList.toMutableList()
                    currentMessages.add(chatMessage)
                    chatAdapter.submitList(currentMessages)

                    messageInput.text.clear()  // 입력 필드 비우기
                    recyclerView.scrollToPosition(currentMessages.size - 1)  // 새로운 메시지로 스크롤
                    showToast("메시지가 성공적으로 전송되었습니다.")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 오류"
                    showToast("메시지 전송 실패: $errorBody")
                    Log.e("ChatActivity", "응답 코드: ${response.code()}, 오류 내용: $errorBody")
                }
            } catch (e: IOException) {
                showToast("네트워크 오류, 나중에 다시 시도해 주세요.")
            } catch (e: HttpException) {
                showToast("메시지 전송 실패: ${e.code()}")
            }
        }
    }

    // 토스트 메시지를 표시하는 함수
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
