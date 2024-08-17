package com.example.readme.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartImgViewModel : ViewModel(){

   private  val repository = StartImgRepository()
    private val _imageList = MutableLiveData<List<StartImage>>()
    val imageList: LiveData<List<StartImage>> get() = _imageList

    init {
        loadImages()
    }

    private fun loadImages() {
        _imageList.value = repository.getImages()
    }
    fun onStartButtonClick() {
        // 시작 버튼 클릭 로직을 여기에 추가
    }
}