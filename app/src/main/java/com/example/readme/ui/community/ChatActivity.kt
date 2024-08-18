package com.example.readme.ui.community

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.databinding.ActivityChatBinding
import com.example.readme.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val chatList = mutableListOf<Chat>()
    private var userId = ""
    private val communityId = "1" // Example community ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = "abcd"

//        userId = intent.getStringExtra(USERID) ?: ""
//        if (userId.isEmpty()) {
//            Log.e("ChatActivity", "User ID is empty")
//            finish()
//            return
//        }

        if (userId.isEmpty()) {
            finish()
        } else {
            chatAdapter = ChatAdapter()

            binding.chatRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = chatAdapter
            }

            binding.sentButton.setOnClickListener {
                val content = binding.messageBox.text.toString()
                if (content.isNotEmpty()) {
                    val chat = Chat(
                        messageId = 0, // Server generates the message ID
                        userId = userId.toInt(), // Assuming userId is an Int
                        content = content,
                        createdAt = "",
                        isSelf = true // Message sent by the user, so set isSelf to true
                    )
                    RetrofitClient.getReadmeServerService().postMessage(communityId, chat).enqueue(object : Callback<Chat> {
                        override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    chatList.add(it)
                                    chatAdapter.submitChat(chatList)
                                    binding.messageBox.setText("")
                                }
                            }
                        }

                        override fun onFailure(call: Call<Chat>, t: Throwable) {
                            // Handle failure
                        }
                    })
                }
            }

            fetchMessages()
        }
    }

    private fun fetchMessages() {
        RetrofitClient.getReadmeServerService().getMessages(communityId).enqueue(object : Callback<List<Chat>> {
            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        chatList.clear()
                        chatList.addAll(it)
                        chatAdapter.submitChat(chatList)
                    }
                }
            }

            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                // Handle failure
                Log.e("ChatActivity", "Network error", t)
            }
        })
    }

    companion object {
        const val USERID = "userid"
    }
}
