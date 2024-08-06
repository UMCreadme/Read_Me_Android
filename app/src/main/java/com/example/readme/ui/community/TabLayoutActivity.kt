package com.example.readme.ui.community

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.readme.R
import com.example.readme.ui.search.SearchFragment
import com.example.tablayout.CreateFragment
import com.example.tablayout.FragmentAdapter
import com.example.tablayout.MyChatFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var viewPager2 = findViewById<ViewPager2>(R.id.viewPager)
        var tablayout = findViewById<TabLayout>(R.id.tablayout)

        val fragmentAdapter = FragmentAdapter(this)
        fragmentAdapter.addFragment(SearchFragment(), "탐색하기")
        fragmentAdapter.addFragment(CreateFragment(), "개설하기")
        fragmentAdapter.addFragment(MyChatFragment(), "나의 채팅방")

        viewPager2.adapter = fragmentAdapter

        // TabLayoutMediator 사용하여 TabLayout과 ViewPager2 연결
        TabLayoutMediator(tablayout, viewPager2) { tab, position ->
            tab.text = fragmentAdapter.getPageTitle(position)
        }.attach()
    }
}