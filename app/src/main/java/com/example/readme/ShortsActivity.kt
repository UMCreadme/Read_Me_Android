package com.example.readme

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.readme.databinding.ActivityShortsBinding

class ShortsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShortsBinding
    private fun initSearchView() {
        // init SearchView
        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // @TODO 검색버튼 누를 때 호출
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // @TODO 검색 창에서 글자가 변경이 일어날 때마다 호출
                return true
            }
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shorts)
        binding = ActivityShortsBinding.inflate(layoutInflater)

    }
}

