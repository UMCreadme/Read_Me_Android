package com.example.readme.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R

class CommunitySearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var communityAdapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout inflater를 사용해 fragment layout을 inflate
        val rootView = inflater.inflate(R.layout.fragment_community_search, container, false)

        // RecyclerView 설정
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 데이터 설정 (임시 데이터 예시)
        val communityList = listOf(
            Community(
                imageResId = R.drawable.book,
                location = "서울 홍대",
                title = "우리는 빛의 속도로 갈 수 있다!!",
                currentMembers = 2,
                totalMembers = 6,
                tags = listOf("#20대끼리", "#김초엽작가님덕질하실분")
            )
            // 여기에 더 많은 아이템을 추가
        )

        // 어댑터 설정
        communityAdapter = CommunityAdapter(communityList)
        recyclerView.adapter = communityAdapter

        return rootView
    }
}
