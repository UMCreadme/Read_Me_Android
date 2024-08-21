package com.example.readme.ui.home.main

import Feed2Adapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.data.entities.category.FeedInfo
import com.example.readme.databinding.FragmentDynamicBinding
import com.example.readme.ui.MainActivity
import com.example.readme.data.entities.inithome.ShortsInfo
import com.example.readme.ui.base.BaseFragment

import com.example.readme.ui.home.Feed.FeedAdapter
import com.example.readme.ui.home.Feed.FeedViewModel
import com.example.readme.ui.home.Feed.ShortsAdapter
import com.example.readme.ui.home.shortsdetail.ShortsDetailFragment
import com.example.readme.ui.profile.UserProfileFragment

class CategoryDynamicFragment : BaseFragment<FragmentDynamicBinding>(R.layout.fragment_dynamic) {

    private lateinit var feedAdapter: FeedAdapter
    private lateinit var feedListManager: LinearLayoutManager
    private lateinit var feed2Adapter: Feed2Adapter
    private lateinit var feed2ListManager: LinearLayoutManager
    private lateinit var shortsAdapter: ShortsAdapter
    private lateinit var shortsListManager: LinearLayoutManager

    // ViewModel 초기화
    private val feedViewModel: FeedViewModel by viewModels()
    private var category: String? = null
    private var isFirstLoad: Boolean = true

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowHome()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        category = arguments?.getString(ARG_CATEGORY)
        Log.d("CategoryDynamicFragment", "Category: $category")

        // 이미 데이터를 로드했는지 확인
        if (isFirstLoad) {
            isFirstLoad = false  // 데이터 로드를 시작했음을 표시

            // 카테고리에 따라 적절한 데이터 로드 메서드 호출
            if (category == "추천") {
                feedViewModel.fetchFeeds()
            } else {
                feedViewModel.fetchCategoryFeeds(category!!)
            }
        }

        feedViewModel.combinedData.observe(viewLifecycleOwner) { (feeds, shorts) ->
            Log.d("FeedViewModel", "Combined Data - Feeds: $feeds")
            Log.d("FeedViewModel", "Combined Data - Shorts: $shorts")
            if (feeds.isNotEmpty() || shorts.isNotEmpty()) {
                setupRecyclerView(feeds, shorts)
            }

        }

        feedViewModel.categoryFeeds.observe(viewLifecycleOwner) { categoryFeeds ->
//            Log.d("FeedViewModel", "Combined Data - categoryFeeds: $categoryFeeds")
            setupCategoryRecyclerView(categoryFeeds)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(feeds: List<com.example.readme.data.entities.inithome.FeedInfo>, shorts: List<ShortsInfo>) {
        if (!::feedAdapter.isInitialized) {
            feedListManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            feedAdapter = FeedAdapter(feedViewModel, ArrayList(feeds))
            binding.rvPost.apply {
                setHasFixedSize(true)
                layoutManager = feedListManager
                adapter = feedAdapter

                // feedAdapter 초기화 완료 후 클릭 리스너 설정
                feedAdapter.setMyItemClickListener(object : FeedAdapter.MyItemClickListener {
                    override fun onItemClick(feed: com.example.readme.data.entities.inithome.FeedInfo) {
                        // 아이템 전체 클릭 시의 동작 (기존 코드)
                    }

                    override fun onImageClick(feed: com.example.readme.data.entities.inithome.FeedInfo) {
                        // 이미지 클릭 시 ShortsDetailFragment로 이동
                        val fragment = ShortsDetailFragment().apply {
                            arguments = Bundle().apply {
                                putInt("shortsId", feed.shortsId)
                                putString("start", "main")
                            }
                        }
                        (context as? MainActivity)?.addFragment(fragment)
                    }

                    override fun onLikeClick(feed: com.example.readme.data.entities.inithome.FeedInfo, isLiked: Boolean) {
                        feedViewModel.likeShorts(feed)
                    }

                    override fun onProfileClick(feed: com.example.readme.data.entities.inithome.FeedInfo) {
                        // 프로필 클릭 시 Profile 이동
                        val fragment = UserProfileFragment().apply {
                            arguments = Bundle().apply {
                                putInt("userId", feed.userId)
                                Log.d("profile", "${feed.userId}")
                            }
                        }
                        (context as? MainActivity)?.addFragment(fragment)
                    }
                })
            }
        } else {
            feedAdapter.updateData(feeds)  // 데이터 갱신 메서드
        }

        if (!::shortsAdapter.isInitialized) {
            shortsListManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            shortsAdapter = ShortsAdapter(ArrayList(shorts))
            binding.rvExtra.apply {
                setHasFixedSize(true)
                layoutManager = shortsListManager
                adapter = shortsAdapter // feedAdapter 초기화 완료 후 클릭 리스너 설정
                shortsAdapter.setMyItemClickListener(object : ShortsAdapter.MyItemClickListener {
                    override fun onItemClick(shorts: ShortsInfo) {
                        val fragment = ShortsDetailFragment().apply {
                            arguments = Bundle().apply {
                                putInt("shorts_id", shorts.shorts_id)
                                putString("start", "main")
                            }
                        }
                        (context as? MainActivity)?.addFragment(fragment)
                    }

                })
            }
        } else {
            shortsAdapter.updateData(shorts)
        }

//        // Show or hide rvExtra based on category
        binding.rvExtra.visibility = if (category == "추천") View.VISIBLE else View.GONE
    }

    private fun setupCategoryRecyclerView(categoryFeeds: List<FeedInfo>) {
        if (!::feed2Adapter.isInitialized) {
            feed2ListManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            feed2Adapter = Feed2Adapter(feedViewModel, ArrayList(categoryFeeds))  // 적절한 Adapter를 사용
            binding.rvPost.apply {
                setHasFixedSize(true)
                layoutManager = feed2ListManager
                adapter = feed2Adapter

                feed2Adapter.setMyItemClickListener(object : Feed2Adapter.MyItemClickListener {
                    override fun onItemClick(feed: FeedInfo) {
                        // 아이템 전체 클릭 시의 동작 (기존 코드)
                    }

                    override fun onImageClick(feed: FeedInfo) {
                        val fragment = ShortsDetailFragment().apply {
                            arguments = Bundle().apply {
                                putInt("shortsId", feed.shortsId)
                                putString("start", "main")
                            }
                        }
                        (context as? MainActivity)?.addFragment(fragment)
                    }

                    override fun onLikeClick(feed: FeedInfo, isLiked: Boolean) {
                        feedViewModel.likeShorts2(feed)
                    }

                    override fun onProfileClick(feed: FeedInfo) {
                        // 프로필 클릭 시 Profile 이동
                        val fragment = UserProfileFragment().apply {
                            arguments = Bundle().apply {
                                putInt("userId", feed.userId)
                                Log.d("profile", "${feed.userId}")
                            }
                        }
                        (context as? MainActivity)?.addFragment(fragment)
                    }
                })
            }
        } else {
            feed2Adapter.updateData(categoryFeeds)
        }
    }


    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String) = CategoryDynamicFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, category)
            }
        }
    }

}
