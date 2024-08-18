package com.example.readme.ui.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.data.entities.ShortsItem
import com.example.readme.data.remote.ProfileShortsItem
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.databinding.FragmentTabRecyclerviewBinding
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch

class MyShortsFragment: Fragment(R.layout.fragment_tab_recyclerview) {

    private lateinit var binding: FragmentTabRecyclerviewBinding
    private lateinit var shortsAdapter: MyShortsAdapter
    private val shortsList = mutableListOf<ShortsItem>()

    private val token: String = "example_token" // 테스트용 사용자 ID
    private val apiService: ReadmeServerService by lazy {
        RetrofitClient.getReadmeServerService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabRecyclerviewBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        shortsAdapter = MyShortsAdapter(shortsList)
        binding.recyclerView.adapter = shortsAdapter

        // API 호출
        fetchShorts()
    }

    private fun fetchShorts() {
        lifecycleScope.launch {
            try {
                val shortsResponse = apiService.getMyShorts(token)
                Log.d("MyShortsFragment", "Fetched shorts: ${shortsResponse.result.size}")
                shortsList.clear()
                shortsList.addAll(shortsResponse.result.toShortsItemList())
                shortsAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // 오류 처리
                Log.e("MyShortsFragment", "Error fetching shorts", e)
            }
        }
    }

    // 타입 변환 함수
    private fun ProfileShortsItem.toShortsItem(): ShortsItem {
        return ShortsItem(
            shortsId = this.shortsId,
            shortsImage = this.shortsImage,
            shortsPhrase = this.shortsPhrase,
            shortsBookTitle = this.shortsBookTitle,
            shortsBookAuthor = this.shortsBookAuthor
        )
    }

    private fun List<ProfileShortsItem>.toShortsItemList(): List<ShortsItem> {
        return this.map { it.toShortsItem() }
    }
}