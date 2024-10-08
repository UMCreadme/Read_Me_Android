package com.example.readme.ui.community.explore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.repository.CommunityRepository
import com.example.readme.databinding.FragmentCommunityExploreBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class CommunityExploreFragment : BaseFragment<FragmentCommunityExploreBinding>(R.layout.fragment_community_explore) {

    private val viewModel: CommunityExploreViewModel by viewModels {
        CommunityExploreViewModelFactory(CommunityRepository)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // RecyclerView에 어댑터 설정
        val adapter = CommunityExploreAdapter(
            onCommunityClick = { communityId ->
                val communityDetailFragment = CommunityDetailFragment()
                val bundle = Bundle().apply {
                    putInt("communityId", communityId)
                }
                communityDetailFragment.arguments = bundle
                (activity as MainActivity).changeFragment(communityDetailFragment)
            }
        )
        binding.communityExploreRecyclerView.adapter = adapter
        binding.communityExploreRecyclerView.layoutManager = LinearLayoutManager(context)

        // 관찰하고 있는 커뮤니티 리스트를 RecyclerView 어댑터에 전달
        viewModel.communityItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

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

        // 검색어 입력에 따라 ViewModel의 searchCommunity 호출
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val keyword = binding.searchEditText.text.toString()
                if (keyword.isEmpty()) {
                    Log.d("CommunityExploreFragment", "keyword is empty")
                    viewModel.fetchCommunityList() // 검색어가 비어 있을 때 전체 커뮤니티 리스트 조회
                } else {
                    viewModel.searchCommunity(keyword)
                }
            }
        })

        // 페이지네이션 적용
        binding.communityExploreRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                        if (binding.searchEditText.text.isNotEmpty()) {
                            viewModel.loadSearchCommunityMore()
                        } else {
                            viewModel.loadDefaultCommunityMore()
                        }
                    }
                }
            }
        })
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus()
    }
}
