package com.example.readme.ui.community

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R

class CreateFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_fragment)

        val btnGoToChat: Button = findViewById(R.id.btn_go_to_chat)
        btnGoToChat.setOnClickListener {
//            val intent = Intent(this, TabLayoutActivity::class.java)
//            startActivity(intent)
        }
    }
}
