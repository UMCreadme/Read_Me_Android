package com.example.readme.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.readme.R
import com.example.readme.databinding.ActivityMainBinding
import com.example.readme.ui.community.explore.CommunityFragment
import com.example.readme.ui.home.main.HomeFragment
import com.example.readme.ui.mypage.MyPageFragment
import com.example.readme.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Readme)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showInit()
        setupBottomNavigationView()

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        val lp = window.attributes
        lp.flags = lp.flags and WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN.inv()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp
    }

    override fun onBackPressed() {
        // 백스택이 비어 있지 않으면 popBackStack
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }
        // 백스택에 남은 Fragment가 하나일 경우 (초기화면)
        else if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
            // 추가 동작을 수행하거나, 앱 종료를 막을 수 있음
            Toast.makeText(this, "뒤로 가기를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        else {
            super.onBackPressed() // 기본 동작 (앱 종료)
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
                    changeFragment(CommunityFragment())
                }
                R.id.navigation_mypage -> {
                    changeFragment(MyPageFragment())
                }
            }
            true
        }
        binding.bottomNavigationView.setOnItemReselectedListener {  } // 바텀네비 재클릭시 화면 재생성 방지
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .commit {
//                setCustomAnimations(
//                    R.anim.slide_3,
//                    R.anim.fade_out,
//                    R.anim.slide_1,
//                    R.anim.fade_out
//                )
                replace(R.id.nav_host_fragment, fragment)

                addToBackStack(fragment.javaClass.simpleName)
            }
    }

    private fun showInit() {
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, HomeFragment())
        transaction.commit()
    }


    fun ShowHome(){
        binding.btnNext.visibility = View.GONE
        binding.tvTitle.visibility = View.GONE
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
    }


    fun ShowCommunity(){
        binding.toolbar.visibility = View.GONE
        binding.mainLogo.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
        binding.tvTitle.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
    }

    fun resetToolbar() {
        binding.tvTitle.text = "프레이즈 만들기"
        binding.btnNext.text = "미리 보기"
    }

    fun ShowSearch(){
        binding.toolbar.visibility = View.GONE
        binding.mainLogo.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
    }

    fun makeShorts(){
        binding.mainLogo.visibility = View.GONE
        binding.tvTitle.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
    }

    fun NoShow(){
        binding.mainLogo.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
        binding.tvTitle.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.VISIBLE
    }


    fun ShowMyPage(){
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.VISIBLE
        binding.btnBack.visibility = View.GONE
    }
}

