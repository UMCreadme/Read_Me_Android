package com.example.readme.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.R
import com.example.readme.databinding.FragmentCommunitySearchBinding

class CommunitySearchFragment : Fragment() {

    private lateinit var binding: FragmentCommunitySearchBinding
    private val communityViewModel: CommunityViewModel by viewModels()
    private lateinit var communityAdapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_search, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = communityViewModel

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        communityAdapter = CommunityAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = communityAdapter
        }


        communityViewModel.communityItems.observe(viewLifecycleOwner, {
            communityAdapter.submitList(it)
        })
    }
}