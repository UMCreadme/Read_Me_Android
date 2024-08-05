package com.example.readme.ui.userinfo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.readme.R
import com.example.readme.databinding.ActivityUserinfoBinding


class UserinfoActivity :AppCompatActivity() {
    private lateinit var binding: ActivityUserinfoBinding
    private val viewModel: UserinfoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validateNickname(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.idEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validateId(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.nicknameError.observe(this, Observer { error ->
            binding.nicknameErrorTv.text = error
            binding.nicknameErrorTv.visibility = if (error != null) View.VISIBLE else View.GONE
        })

        viewModel.idError.observe(this, Observer { error ->
            binding.idnameErrorTv.text = error
            binding.idnameErrorTv.visibility = if (error != null) View.VISIBLE else View.GONE
        })
    }
}