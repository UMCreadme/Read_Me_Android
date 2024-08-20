package com.example.readme.ui.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R

class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Fragment가 이미 추가된 상태가 아니면 추가
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookDetailFragment())
                .commit()
        }
    }
}
