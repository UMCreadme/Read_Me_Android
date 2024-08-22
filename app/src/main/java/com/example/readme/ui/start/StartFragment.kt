package com.example.readme.ui.start

import ImagePagerAdapter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.readme.R
import com.example.readme.databinding.FragmentStartBinding
import com.example.readme.ui.login.LoginActivity
import me.relex.circleindicator.CircleIndicator3

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StartImgViewModel
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("StartFragment", "onViewCreated called")

        viewModel = ViewModelProvider(this).get(StartImgViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupViewPager()
        setupButtonState()

        // LiveData 관찰 및 LoginActivity의 UI 전환 처리
        binding.startBtn.setOnClickListener {
            Log.d("StartFragment", "Start button clicked")
            (activity as? LoginActivity)?.showLoginUI()
        }

    }

    private fun setupViewPager() {
        viewModel.imageList.observe(viewLifecycleOwner, { images ->
            val adapter = ImagePagerAdapter(images)
            binding.startVp.adapter = adapter
            val indicator = binding.root.findViewById<CircleIndicator3>(R.id.start_indicator)
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
            Log.d("StartFragment", "Start button clicked")
            (activity as? LoginActivity)?.showLoginUI()
        }
    }

    private fun updateButtonState(isLastPage: Boolean) {
        if (isLastPage) {
            binding.startBtn.isEnabled = true
            binding.startBtn.setBackgroundColor(resources.getColor(R.color.Primary))
            binding.startBtn.setOnClickListener {
                Log.d("StartFragment", "Start button clicked from updateButtonState")
                (activity as? LoginActivity)?.showLoginUI()
            }
        } else {
            binding.startBtn.isEnabled = false
            binding.startBtn.setBackgroundColor(resources.getColor(R.color.Light_Gray))
            binding.startBtn.setOnClickListener(null) // 클릭 리스너를 제거
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // handler가 초기화 되었을 때만 removeCallbacks 호출
        handler?.removeCallbacks(runnable!!)
        _binding = null
    }
}
