package com.example.readme.ui.community.create

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.readme.R
import com.example.readme.data.entities.PostCommunityRequest
import com.example.readme.data.repository.CommunityRepository
import com.example.readme.databinding.FragmentCommunityCreateBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment

class CommunityCreateFragment : BaseFragment<FragmentCommunityCreateBinding>(R.layout.fragment_community_create) {

    private val viewModel: CommunityCreateViewModel by viewModels {
        CommunityCreateViewModelFactory(CommunityRepository)
    }
    private var selectedPlace: String? = null
    private lateinit var placeTextViews: List<TextView>

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        placeTextViews = listOf(binding.placeSeoul, binding.placeBusan, binding.placeDaegu, binding.placeIncheon,
            binding.placeGwangju, binding.placeDaejeon, binding.placeUlsan, binding.placeSejong, binding.placeGyeonggi,
            binding.placeGangwon, binding.placeChungbuk, binding.placeChungnam, binding.placeJeonbuk, binding.placeJeonnam,
            binding.placeGyeongbuk, binding.placeGyeongnam, binding.placeJeju)

        val bookCover = arguments?.getString("bookCover")
        val bookTitle = arguments?.getString("bookTitle")
        val isbn = arguments?.getString("ISBN") as String
        val author = arguments?.getString("author")

        binding.bookClubNameEditText.text = bookTitle
        binding.bookTitleTextView.text = bookTitle
        binding.bookAuthorTextView.text = author

        Glide.with(binding.root.context)
            .load(bookCover)
            .placeholder(R.drawable.img_1)
            .into(binding.bookImageView)

        // 리스너 설정
        binding.descriptionEditText.addTextChangedListener(textWatcher)
        binding.tagsEditText.addTextChangedListener(tagsTextWatcher)
        binding.participantsEditText.addTextChangedListener(textWatcher)
        placeTextViews.forEach { placeTextView ->
            placeTextView.setOnClickListener {
                selectedPlace = placeTextView.text.toString()
                updateSelectedPlaceUI(placeTextView)
                validateFields()
            }
        }

        // 초기 버튼 상태 설정
        validateFields()
        binding.updateButton.setOnClickListener {
            if(validateFields()) {
                val content = binding.descriptionEditText.text.toString()
                val tags = convertTagsForServer()
                val capacity = binding.participantsEditText.text.toString()
                val location = selectedPlace!!
                val community = PostCommunityRequest(isbn, content, tags, location, capacity.toInt())
                viewModel.createCommunity(community)
            }
        }

        viewModel.communityCreate.observe(viewLifecycleOwner) { response ->
            if (response.isSuccess) {
                (activity as MainActivity).changeFragment(CommunityCreateDoneFragment())
            } else {
                Toast.makeText(context, "커뮤니티 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 태그 리스트를 분리하여 반환
    private fun convertTagsForServer(): String {
        val tagsText =
            binding.tagsEditText.text.toString().trim().split(" ").filter { it.isNotEmpty() }
        return tagsText.joinToString("|") { it.substring(1) }
    }

    // 일반 TextWatcher
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    // 태그 TextWatcher
    private val tagsTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val tags = s.toString().trim()
            val tagList = tags.split("#").filter { it.isNotEmpty() }

            // 태그의 개수와 각 태그의 길이를 체크
            if (tagList.size > 10 || tagList.any { it.length > 10 }) {
                binding.tagsEditText.removeTextChangedListener(this)
                binding.tagsEditText.setText(tags.substring(0, start))
                binding.tagsEditText.setSelection(start)
                binding.tagsEditText.addTextChangedListener(this)

                // 토스트 메시지 표시
                Toast.makeText(context, "글자 수는 10개, 태그의 개수는 10개로 제한합니다.", Toast.LENGTH_SHORT).show()
            }
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateFields(): Boolean {
        val description = binding.descriptionEditText.text.toString().trim()
        val tags = binding.tagsEditText.text.toString().trim()
        val participantsText = binding.participantsEditText.text.toString().trim()
        val participants = participantsText.toIntOrNull()

        val isDescriptionValid = description.length <= 300
        val isTagsValid = tags.split("#").all { it.length <= 10 } && tags.split("#").size <= 10
        val isParticipantsValid = participants != null && participants in 4..10
        val isPlaceSelected = selectedPlace != null

        val isFormValid = isDescriptionValid && isTagsValid && isParticipantsValid && isPlaceSelected

        binding.updateButton.isEnabled = isFormValid
        binding.updateButton.setBackgroundColor(
            if (isFormValid) ContextCompat.getColor(requireContext(), R.color.Primary)
            else ContextCompat.getColor(requireContext(), R.color.Light_Gray)
        )
        return isFormValid
    }

    private fun updateSelectedPlaceUI(selectedTextView: TextView) {
        placeTextViews.forEach { placeTextView ->
            placeTextView.setBackgroundColor(
                if (placeTextView == selectedTextView) ContextCompat.getColor(requireContext(), R.color.Primary)
                else ContextCompat.getColor(requireContext(), R.color.Light_Gray)
            )
            placeTextView.setTextColor(
                if (placeTextView == selectedTextView) ContextCompat.getColor(requireContext(), R.color.White)
                else ContextCompat.getColor(requireContext(), R.color.Black)
            )
        }
    }
}
