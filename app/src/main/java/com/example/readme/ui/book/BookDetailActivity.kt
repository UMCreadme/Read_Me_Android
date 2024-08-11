package com.example.readme.ui.book

import com.example.readme.R
import com.example.readme.databinding.ActivityBookDetailBinding
import com.example.readme.ui.base.BaseFragment

class BookDetailActivity : BaseFragment<ActivityBookDetailBinding>(R.layout.activity_book_detail) {
    override fun initDataBinding() {
        super.initDataBinding()
        binding.lifecycleOwner = this

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        val bookId = arguments?.getInt("book_id") ?: 0

        // 읽음 버튼 리스너

    }

}