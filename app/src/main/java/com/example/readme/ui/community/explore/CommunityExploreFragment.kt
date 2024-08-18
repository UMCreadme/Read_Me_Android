package com.example.readme.ui.community.explore

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.example.readme.data.repository.CommunityRepository
import com.example.readme.databinding.FragmentCommunityExploreBinding
import com.example.readme.ui.base.BaseFragment

class CommunityExploreFragment : BaseFragment<FragmentCommunityExploreBinding>(R.layout.fragment_community_explore) {

    private val viewModel: CommunityExploreViewModel by viewModels {
        CommunityExploreViewModelFactory(CommunityRepository)
    }

    override fun initDataBinding() {
        super.initAfterBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // RecyclerView에 LinearLayoutManager 설정
        binding.communityExploreRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // RecyclerView에 어댑터 설정
        val adapter = CommunityExploreAdapter(
            onCommunityClick = { communityId ->
                // 커뮤니티 상세 화면으로 전환
//                val communityDetailFragment = CommunityDetailFragment()
//                val bundle = Bundle()
//                bundle.putString("communityId", communityId)
//                communityDetailFragment.arguments = bundle
//                (activity as MainActivity).addFragment(communityDetailFragment)
            }
        )
        binding.communityExploreRecyclerView.adapter = adapter

        viewModel.fetchCommunityList()

        // 관찰하고 있는 커뮤니티 리스트를 RecyclerView 어댑터에 전달
        viewModel.communityItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.communityExploreRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter?.itemCount ?: 0

                    if (lastVisibleItemPosition + 1 == itemTotalCount) {
                        viewModel.loadDefaultCommunityMore()
                    }
                }
            }
        })
    }
}