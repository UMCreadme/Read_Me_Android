package com.example.readme.ui.community.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.fragment.app.Fragment
import com.example.readme.R
import com.example.readme.databinding.FragmentCommunityCreateBinding
import com.example.readme.ui.base.BaseFragment

class CommunityCreateFragment : BaseFragment<FragmentCommunityCreateBinding>(R.layout.fragment_community_create) {
    override fun initStartView() {
        super.initStartView()
        // TODO: 최근 선택한 책 불러오는 Fragment로 설정
         setFragment(RecentSelectBookFragment())
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // 검색 버튼 클릭 시 키보드 숨김
        binding.searchButton.setOnClickListener {
            hideKeyboard()
        }

        // EditText 검색 버튼 클릭 시 키보드 숨김
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP && binding.searchEditText.text.isNotEmpty()) {
                hideKeyboard()
                true
            } else if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // EditText 텍스트 변경 시 책 검색
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val bookSearchResultFragment = BookSearchResultFragment()
                val bundle = Bundle()
                bundle.putString("keyword", binding.searchEditText.text.toString())
                bookSearchResultFragment.arguments = bundle
                setFragment(bookSearchResultFragment)
            }
        })
    }

    fun hideKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus() // 포커스 해제
    }

    fun setFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.search_section_fragment, fragment)
            .commit()
    }
}