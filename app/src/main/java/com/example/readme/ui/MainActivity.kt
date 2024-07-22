package com.example.readme.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.readme.R
import com.example.readme.databinding.ActivityMainBinding
import com.example.readme.ui.community.CommunityFragment
import com.example.readme.ui.home.HomeFragment
import com.example.readme.ui.mypage.MyPageFragment
import com.example.readme.ui.search.SearchFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Readme)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupBottomNavigationView()

    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.navigation_home -> HomeFragment()
                R.id.navigation_search -> SearchFragment()
                R.id.navigation_community -> CommunityFragment()
                R.id.navigation_mypage -> MyPageFragment()
                else -> null
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, it)
                    .commit()
            }
            true
        }
        // Set default selection
        binding.bottomNavigationView.selectedItemId = R.id.navigation_home
    }

}

