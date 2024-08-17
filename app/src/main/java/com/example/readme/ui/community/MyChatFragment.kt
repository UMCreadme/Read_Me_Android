package com.example.readme.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R

class MyChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommunityAdapter
    private val chatList = listOf(
        Community(
            imageResId = R.drawable.book, // 적절한 이미지 리소스 ID로 대체하세요
            location = "서울 홍대",
            title = "우리는 빛의 속도로 갈 수 있다!!",
            currentMembers = 2,
            totalMembers = 6,
            tags = listOf("#20대끼리", "#김초엽작가님덕질하실분")
        ),
        Community(
            imageResId = R.drawable.book, // 적절한 이미지 리소스 ID로 대체하세요
            location = "서울 강남",
            title = "나는 자유다!!",
            currentMembers = 3,
            totalMembers = 8,
            tags = listOf("#자유롭게", "#함께가자")
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_chat, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
//        adapter = CommunityAdapter(chatList) // TODO: 승연님이 사용하는 어댑터와 지선님이 사용하는 어댑터 상이

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}
