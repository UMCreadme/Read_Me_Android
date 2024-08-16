package com.example.readme.ui.home.main

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
import com.example.readme.ui.home.Feed.Feed2Adapter
import com.example.readme.ui.home.Feed.FeedAdapter
import com.example.readme.ui.home.Feed.FeedViewModel
import com.example.readme.ui.home.Feed.ShortsAdapter
import com.example.readme.ui.home.shortsdetail.ShortsDetailFragment

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

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowHome()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        category = arguments?.getString(ARG_CATEGORY)
        Log.d("CategoryDynamicFragment", "Category: $category")

        if (category == "추천") {
            feedViewModel.fetchFeeds()
        } else {
            feedViewModel.fetchCategoryFeeds(category!!)
        }


        feedViewModel.combinedData.observe(viewLifecycleOwner) { (feeds, shorts) ->
            Log.d("FeedViewModel", "Combined Data - Feeds: $feeds")
            Log.d("FeedViewModel", "Combined Data - Shorts: $shorts")
            setupRecyclerView(feeds, shorts)
        }

        feedViewModel.categoryFeeds.observe(viewLifecycleOwner) { categoryFeeds ->
            Log.d("FeedViewModel", "Category Feeds: $categoryFeeds")
            setupCategoryRecyclerView(categoryFeeds)
        }
    }

    private fun setupRecyclerView(feeds: List<com.example.readme.data.entities.inithome.FeedInfo>, shorts: List<ShortsInfo>) {
        if (!::feedAdapter.isInitialized) {
            feedListManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            feedAdapter = FeedAdapter(ArrayList(feeds))
            binding.rvPost.apply {
                setHasFixedSize(true)
                layoutManager = feedListManager
                adapter = feedAdapter
                // feedAdapter 초기화 완료 후 클릭 리스너 설정
                feedAdapter.setMyItemClickListener(object : FeedAdapter.MyItemClickListener {
                    override fun onItemClick(feed: com.example.readme.data.entities.inithome.FeedInfo) {

                        val fragment = ShortsDetailFragment().apply {
                            arguments = Bundle().apply {
                                putInt("shortsId", feed.shortsId)
                                Log.d("shortId", feed.shortsId.toString())
                                putString("start", "main")
                                // 필요한 경우 추가 데이터도 함께 전달
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
                adapter = shortsAdapter
            }
        } else {
            shortsAdapter.updateData(shorts)
        }

        // Show or hide rvExtra based on category
        binding.rvExtra.visibility = if (category == "추천") View.VISIBLE else View.GONE
    }

    private fun setupCategoryRecyclerView(categoryFeeds: List<FeedInfo>) {
        if (!::feed2Adapter.isInitialized) {
            feed2ListManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            feed2Adapter = Feed2Adapter(ArrayList(categoryFeeds))  // 적절한 Adapter를 사용
            binding.rvPost.apply {
                setHasFixedSize(true)
                layoutManager = feed2ListManager
                adapter = feed2Adapter

                feed2Adapter.setMyItemClickListener(object : Feed2Adapter.MyItemClickListener {
                    override fun onItemClick(feed: FeedInfo) {
                        val fragment = ShortsDetailFragment().apply {
                            arguments = Bundle().apply {
                                putInt("shortsId", feed.shortsId)
                                Log.d("shortId", feed.shortsId.toString())
                                putString("start", "main")
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


    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
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
