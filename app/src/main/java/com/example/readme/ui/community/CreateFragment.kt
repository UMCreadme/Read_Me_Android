package com.example.readme.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readme.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: CreateViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = BookAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = bookAdapter

        searchViewModel.books.observe(viewLifecycleOwner) { books: List<Book> ->
            bookAdapter.updateBooks(books)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.filterBooks(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
