package com.example.readme.ui.community.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.readme.R
import com.example.readme.data.remote.ReadmeServerService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommunityDetailJoinFragment : Fragment() {

    private var communityId: Int? = null
    private var userId: Int? = null

    // Retrofit 인스턴스 초기화
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.umcreadme11.shop") // 실제 API의 base URL로 수정
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ReadmeServerService::class.java)
    }

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
        return inflater.inflate(R.layout.fragment_community_detail_join, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communityId?.let { id ->
            fetchCommunityDetail(id)
        }
    }

    private fun fetchCommunityDetail(communityId: Int) {
        lifecycleScope.launch {
            try {
                val response = apiService.getCommunityDetail(communityId)
                userId = response.communityId
                userId?.let {
                    Toast.makeText(requireContext(), "$it 님 안녕하세요", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error fetching community details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(communityId: Int): CommunityDetailJoinFragment {
            val fragment = CommunityDetailJoinFragment()
            val args = Bundle()
            args.putInt("COMMUNITY_ID", communityId)
            fragment.arguments = args
            return fragment
        }
    }
}
