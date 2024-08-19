
package com.example.readme.ui.start

import ImagePagerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.readme.R
import com.example.readme.databinding.ActivityStartBinding
import com.example.readme.ui.login.LoginActivity
import com.example.readme.ui.start.StartImgViewModel
import me.relex.circleindicator.CircleIndicator3

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var viewModel: StartImgViewModel
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(StartImgViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViewPager()
        setupAutoSlide()

        // LiveData 관찰 및 LoginActivity로의 전환 처리
        viewModel.navigateToLogin.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                navigateToLogin()
                viewModel.onNavigatedToLogin() // 상태 초기화
            }
        })
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupViewPager() {
        viewModel.imageList.observe(this, { images ->
            val adapter = ImagePagerAdapter(images)
            binding.startVp.adapter = adapter
            val indicator = findViewById<CircleIndicator3>(R.id.start_indicator)
            indicator.setViewPager(binding.startVp)
        })
    }

    private fun setupAutoSlide() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                var currentItem = binding.startVp.currentItem
                val totalItem = binding.startVp.adapter?.itemCount ?: 0
                currentItem = (currentItem + 1) % totalItem
                binding.startVp.setCurrentItem(currentItem, true)
                handler.postDelayed(this, 3000) // 3초마다 슬라이드
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
