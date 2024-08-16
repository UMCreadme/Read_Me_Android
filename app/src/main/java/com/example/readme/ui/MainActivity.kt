package com.example.readme.ui

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.readme.R
import com.example.readme.databinding.ActivityMainBinding
import com.example.readme.ui.community.CommunitySearchFragment
import com.example.readme.ui.home.HomeFragment
import com.example.readme.ui.mypage.MyPageFragment
import com.example.readme.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Readme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ShowInit()
        setupBottomNavigationView()
        setSupportActionBar(binding.toolbar)
        // Disable displaying the title in the Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager
                .popBackStack()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.itemIconTintList = null

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> {
                    changeFragment(HomeFragment())
                }

                R.id.navigation_search -> {
                    addFragment(SearchFragment())
                }

                R.id.navigation_community -> {
                    changeFragment(CommunitySearchFragment())
                }
                R.id.navigation_mypage -> {
                    changeFragment(MyPageFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigationView.setOnItemReselectedListener {  } // 바텀네비 재클릭시 화면 재생성 방지
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .commit {
                setCustomAnimations(
                    R.anim.slide_3,
                    R.anim.fade_out,
                    R.anim.slide_1,
                    R.anim.fade_out
                )
                replace(R.id.nav_host_fragment, fragment)

                addToBackStack(null)
            }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

    fun ShowInit(){
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
    }


    fun ShowHome(){
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
    }

    fun ShowSearch(){
        binding.toolbar.visibility = View.GONE
        binding.mainLogo.visibility = View.GONE
        binding.btnFilter.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
    }

    fun ShowCommunity() {
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
    }

    fun ShowMyPage(){
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.GONE
        binding.btnSetting.visibility = View.VISIBLE
        binding.btnBack.visibility = View.GONE
    }
}
