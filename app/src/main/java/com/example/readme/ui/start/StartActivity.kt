package com.example.readme.ui.start

import ImagePagerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.readme.R
import com.example.readme.databinding.ActivityStartBinding
import com.example.readme.ui.login.LoginActivity
import me.relex.circleindicator.CircleIndicator3

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var viewModel: StartImgViewModel
    private var handler: Handler? = null  // nullable로 변경
    private var runnable: Runnable? = null  // nullable로 변경

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(StartImgViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViewPager()
        setupButtonState()
        //setupAutoSlide() // 자동 슬라이드 설정

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

            // ViewPager2에서 페이지 변경 리스너 설정
            binding.startVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val isLastPage = position == adapter.itemCount - 1
                    updateButtonState(isLastPage)
                }
            })
        })
    }

    private fun setupButtonState() {
        // 초기 상태에서 버튼 비활성화
        binding.startBtn.isEnabled = false
        binding.startBtn.setBackgroundColor(resources.getColor(R.color.Light_Gray))

        binding.startBtn.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun updateButtonState(isLastPage: Boolean) {
        if (isLastPage) {
            binding.startBtn.isEnabled = true
            binding.startBtn.setBackgroundColor(resources.getColor(R.color.Primary))
        } else {
            binding.startBtn.isEnabled = false
            binding.startBtn.setBackgroundColor(resources.getColor(R.color.Light_Gray))
        }
    }

//    private fun setupAutoSlide() {
//        handler = Handler(Looper.getMainLooper())
//        runnable = object : Runnable {
//            override fun run() {
//                var currentItem = binding.startVp.currentItem
//                val totalItem = binding.startVp.adapter?.itemCount ?: 0
//                currentItem = (currentItem + 1) % totalItem
//                binding.startVp.setCurrentItem(currentItem, true)
//                handler?.postDelayed(this, 3000) // 3초마다 슬라이드
//            }
//        }
//        handler?.postDelayed(runnable!!, 3000)
//    }

    override fun onDestroy() {
        super.onDestroy()
        // handler가 초기화 되었을 때만 removeCallbacks 호출
        handler?.removeCallbacks(runnable!!)
    }
}
