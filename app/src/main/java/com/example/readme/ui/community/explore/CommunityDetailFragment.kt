package com.example.readme.ui.community.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.readme.R

class CommunityDetailFragment : Fragment() {

    private var communityId: Int? = null
    private var userId: String? = null // 유저 ID를 저장할 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            communityId = it.getInt("communityId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 예시로 communityId에 해당하는 userId를 임의로 설정
        userId = getUserIdForCommunity(communityId)

        // userId가 설정된 경우에만 토스트 메시지 표시
        userId?.let {
            Toast.makeText(requireContext(), "$it 님 안녕하세요", Toast.LENGTH_LONG).show()
        }
    }

    // communityId를 기반으로 userId를 가져오는 예시 함수 (여기서는 임의로 반환)
    private fun getUserIdForCommunity(communityId: Int?): String? {
        // 실제로는 API 호출 등을 통해 userId를 가져오는 로직이 필요
        // 예를 들어, network request를 사용하여 userId를 가져오는 방식
        return "사용자명" // 여기에 실제 userId를 반환하는 로직 추가
    }

    companion object {
        fun newInstance(communityId: Int): CommunityDetailFragment {
            val fragment = CommunityDetailFragment()
            val args = Bundle()
            args.putInt("COMMUNITY_ID", communityId)
            fragment.arguments = args
            return fragment
        }
    }
}
