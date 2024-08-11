package com.example.readme.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.fragment.app.Fragment
import com.example.readme.R
import com.example.readme.databinding.FragmentSearchBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.search.book.BookSearchPreviewFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowSearch()
        setFragment(RecentSearchFragment())
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

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
                    setFragment(RecentSearchFragment())
                } else {
                    // BookSearchPreviewFragment로 전환
                    val searchBookPreviewFragment = BookSearchPreviewFragment()
                    val bundle = Bundle()
                    bundle.putString("keyword", keyword)
                    searchBookPreviewFragment.arguments = bundle
                    setFragment(searchBookPreviewFragment)
                }
            }
        })
    }

    fun hideKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus() // 포커스 해제
    }

    fun setQuery(query: String) {
        binding.searchEditText.setText(query)
    }

    fun setFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.search_section_fragment, fragment)
            .commit()
    }
}
