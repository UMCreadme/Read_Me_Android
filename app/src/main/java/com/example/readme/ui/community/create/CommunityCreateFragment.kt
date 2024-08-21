package com.example.readme.ui.community.create

import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.readme.R
import com.example.readme.data.repository.CommunityRepository
import com.example.readme.databinding.FragmentCommunityCreateBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class CommunityCreateFragment : BaseFragment<FragmentCommunityCreateBinding>(R.layout.fragment_community_create) {

    private val viewModel: CommunityCreateViewModel by viewModels {
        CommunityCreateViewModelFactory(CommunityRepository)
    }

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // 장소 텍스트 뷰 초기화
        var placeTextViews = listOf(
            binding.placeSeoul,
            binding.placeBusan,
            binding.placeDaegu,
            binding.placeIncheon,
            binding.placeGwangju,
            binding.placeDaejeon,
            binding.placeUlsan,
            binding.placeSejong,
            binding.placeGyeonggi,
            binding.placeGangwon,
            binding.placeChungbuk,
            binding.placeChungnam,
            binding.placeJeonbuk,
            binding.placeJeonnam,
            binding.placeGyeongbuk,
            binding.placeGyeongnam,
            binding.placeJeju
        )

        val bookCover = arguments?.getString("bookCover")
        val bookTitle = arguments?.getString("bookTitle")
        val isbn = arguments?.getString("ISBN") as String
        val author = arguments?.getString("author")

        binding.bookAuthorTextView.text = bookTitle
        binding.bookAuthorTextView.text = author

        Glide.with(binding.root.context)
            .load(bookCover)
            .into(binding.bookImageView)

        binding.updateButton.setOnClickListener {
            val content = binding.descriptionEditText.text as String
            val tags = binding.tagsEditText.text as String
            val capacity = binding.participantsEditText.text as String
            //val community = PostCommunityRequest(isbn, content, tags, location, capacity.toInt())
            //viewModel.createCommunity(community)
        }
    }

}