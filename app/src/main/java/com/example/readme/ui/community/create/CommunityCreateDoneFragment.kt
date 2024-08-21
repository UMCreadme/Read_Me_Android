package com.example.readme.ui.community.create

import android.view.View
import com.example.readme.R
import com.example.readme.databinding.FragmentCommunityCreateDoneBinding
import com.example.readme.ui.MainActivity
import com.example.readme.ui.base.BaseFragment
import com.example.readme.ui.community.explore.CommunityFragment

class CommunityCreateDoneFragment : BaseFragment<FragmentCommunityCreateDoneBinding>(R.layout.fragment_community_create_done) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnToMyChat.setOnClickListener {
            (activity as MainActivity).changeFragment(CommunityFragment(2))
        }
    }
}