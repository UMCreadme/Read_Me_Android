package com.example.readme.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.readme.R
import com.example.readme.databinding.ActivityMainBinding
import com.example.readme.ui.community.CommunityFragment
import com.example.readme.ui.home.HomeFragment
import com.example.readme.ui.mypage.MyPageFragment
import com.example.readme.ui.search.BookSearchPreviewFragment
import com.example.readme.ui.search.SearchFragment
import com.example.readme.ui.search.SearchResultFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Readme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ShowInit()
        setupBottomNavigationView()
        setupSearchView()
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
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.navigation_home -> HomeFragment()
                R.id.navigation_search -> SearchFragment()
                R.id.navigation_community -> CommunityFragment()
                R.id.navigation_mypage -> MyPageFragment()
                else -> null
            }
            selectedFragment?.let {
                setFragment(it)
            }
            true
        }
        // Set default selection
        binding.bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    private fun setupSearchView() {
        // 검색 버튼 클릭 시 SearchResultFragment로 이동
        binding.searchButton.setOnClickListener {
            hideKeyboard()
            val searchResultFragment = SearchResultFragment()
            val bundle = Bundle()
            bundle.putString("keyword", binding.searchEditText.text.toString())
            searchResultFragment.arguments = bundle
            setFragment(searchResultFragment)
        }

        // EditText 검색 버튼 클릭 시 SearchResultFragment로 이동
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP && binding.searchEditText.text.isNotEmpty()) {
                hideKeyboard()
                val searchResultFragment = SearchResultFragment()
                val bundle = Bundle()
                bundle.putString("keyword", binding.searchEditText.text.toString())
                searchResultFragment.arguments = bundle
                setFragment(searchResultFragment)
                true
            } else if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // EditText 텍스트 변경 시 책 미리보기 검색
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val keyword = binding.searchEditText.text.toString()
                if (keyword.isEmpty()) {
                    val searchFragment = SearchFragment()
                    setFragment(searchFragment)
                } else {
                    val searchBookPreviewFragment = BookSearchPreviewFragment()
                    val bundle = Bundle()
                    bundle.putString("keyword", keyword)
                    searchBookPreviewFragment.arguments = bundle
                    if (keyword.isNotEmpty()) {
                        setFragment(searchBookPreviewFragment)
                    }
                }
            }
        })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus() // 포커스 해제
    }


    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

    fun ShowInit(){
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
        binding.searchView.visibility = View.GONE
    }

    fun ShowHome(){
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.VISIBLE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
        binding.searchView.visibility = View.GONE
    }

    fun ShowSearch(){
        binding.mainLogo.visibility = View.GONE
        binding.btnFilter.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
        binding.searchView.visibility = View.VISIBLE
    }

    fun ShowCommunity() {
        binding.toolbar.visibility = View.VISIBLE
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.GONE
        binding.btnSetting.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
        binding.searchView.visibility = View.GONE
    }

    fun ShowMyPage(){
        binding.mainLogo.visibility = View.VISIBLE
        binding.btnFilter.visibility = View.GONE
        binding.btnSetting.visibility = View.VISIBLE
        binding.btnBack.visibility = View.GONE
        binding.searchView.visibility = View.GONE
    }
}
