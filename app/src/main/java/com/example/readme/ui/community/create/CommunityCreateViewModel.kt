package com.example.readme.ui.community.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.PostCommunityRequest
import com.example.readme.data.remote.Response
import com.example.readme.data.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityCreateViewModel(private val repository: CommunityRepository): ViewModel() {
    //커뮤 생성부분, 사용자 입력 데이터관리.
    private val _communityCreate = MutableLiveData<Response>()

    val communityCreate : LiveData<Response> get() = _communityCreate

    fun createCommunity(community: PostCommunityRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postCommunity(community)

                _communityCreate.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}