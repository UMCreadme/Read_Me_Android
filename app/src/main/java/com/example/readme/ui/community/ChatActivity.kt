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
import com.example.readme.data.remote.MessageRequest
import com.example.readme.utils.RetrofitClient
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
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
    private val communityId = "50"  // 테스트 커뮤니티 ID
    private val userId = 1  // 현재 사용자 ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize views
        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)  // sentButton으로 수정
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navButton: ImageButton = findViewById(R.id.nav_button)

        // Setup RecyclerView
        chatAdapter = ChatAdapter(userId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        // Setup DrawerLayout and ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up navigation button to open the drawer
        navButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        // Handle navigation item selection
        navView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                // Handle navigation menu item clicks here
            }
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }

        // Fetch existing messages
        fetchMessages()

        // Send message button click listener
        sendButton.setOnClickListener {
            val messageContent = messageInput.text.toString()
            if (messageContent.isNotEmpty()) {
                sendMessage(messageContent)
            }
        }
    }

    private fun fetchMessages() {
        val service = RetrofitClient.getChatFetchService()

        lifecycleScope.launch {
            try {
                val response = service.fetchMessages(communityId)
                if (response.isSuccess) {
                    val messages = response.result ?: emptyList()
                    if (messages.isNotEmpty()) {
                        chatAdapter.submitList(messages)
                        recyclerView.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.GONE
                    }
                } else {
                    showToast("Failed to load messages: ${response.code}")
                }
            } catch (e: IOException) {
                showToast("Network failure, please try again later.")
            } catch (e: HttpException) {
                showToast("Failed to load messages: ${e.code()}")
            }
        }
    }

    private fun sendMessage(content: String) {
        val service = RetrofitClient.getChatFetchService()
        val messageRequest = MessageRequest(content)

        lifecycleScope.launch {
            try {
                val response = service.sendMessage(communityId, messageRequest)
                if (response.isSuccessful) {
                    fetchMessages()
                    messageInput.text.clear()
                } else {
                    showToast("Failed to send message: ${response.code()}")
                }
            } catch (e: IOException) {
                showToast("Network failure, please try again later.")
            } catch (e: HttpException) {
                showToast("Failed to send message: ${e.code()}")
            }
        }
    }

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
}
