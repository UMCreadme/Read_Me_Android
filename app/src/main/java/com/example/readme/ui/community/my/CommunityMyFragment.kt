package com.example.readme.ui.community.my

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.repository.CommunityRepository
import com.example.readme.databinding.FragmentCommunityMyBinding
import com.example.readme.ui.base.BaseFragment

class CommunityMyFragment : BaseFragment<FragmentCommunityMyBinding>(R.layout.fragment_community_my) {

    private val viewModel: CommunityMyViewModel by viewModels {
        CommunityMyViewModelFactory(CommunityRepository)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // RecyclerView에 어댑터 설정
        val adapter = CommunityMyAdapter(
            onCommunityClick = { communityId ->
                Toast.makeText(context, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show()
            }
        )

        binding.communityMyRecyclerView.adapter = adapter
        binding.communityMyRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.fetchMyCommunityList()

        // 관찰하고 있는 커뮤니티 리스트를 RecyclerView 어댑터에 전달
        viewModel.myCommunityItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // 페이지네이션 적용
        binding.communityMyRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                        viewModel.loadMore()
                    }
                }
            }
        })
    }
}
