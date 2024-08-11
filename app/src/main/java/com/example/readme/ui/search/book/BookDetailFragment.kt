package com.example.readme.ui.search.book

import android.util.Log
import com.example.readme.R
import com.example.readme.databinding.FragmentBookDetailBinding
import com.example.readme.ui.base.BaseFragment

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {
    override fun initDataBinding() {
        super.initDataBinding()
        binding.lifecycleOwner = this

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        val ISBN = arguments?.getString("ISBN")
        val bookId = arguments?.getInt("bookId")

        Log.d("BookDetailFragment", "ISBN: $ISBN")
        Log.d("BookDetailFragment", "BookId: $bookId")

        // 읽음 버튼 리스너

    }

}