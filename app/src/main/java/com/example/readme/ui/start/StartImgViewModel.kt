//package com.example.readme.ui.start
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//
//
//class StartImgViewModel : ViewModel(){
//
//   private  val repository = StartImgRepository()
//    private val _imageList = MutableLiveData<List<StartImage>>()
//    val imageList: LiveData<List<StartImage>> get() = _imageList
//
//    init {
//        loadImages()
//    }
//
//    private fun loadImages() {
//        _imageList.value = repository.getImages()
//    }
//    fun onStartButtonClick() {
//        // 시작 버튼 클릭 로직을 여기에 추가
//    }
//}
package com.example.readme.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class StartImgViewModel : ViewModel() {
    private val repository = StartImgRepository()
    private val _imageList = MutableLiveData<List<StartImage>>()
    val imageList: LiveData<List<StartImage>> get() = _imageList

    // 화면 전환을 트리거할 LiveData
    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    init {
        loadImages()
    }

    private fun loadImages() {
        _imageList.value = repository.getImages()
    }

    fun onStartButtonClick() {
        _navigateToLogin.value = true
    }

    // 화면 전환이 완료된 후 LiveData 초기화
    fun onNavigatedToLogin() {
        _navigateToLogin.value = false
    }
}
