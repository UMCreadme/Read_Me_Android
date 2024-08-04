package com.example.readme.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    private  val _selectedCount = MutableLiveData(0)
    val selectedCount : LiveData<Int> get() = _selectedCount
    private val maxSelections = 8

    fun toggleSelection(isSelected : Boolean){
        val count = _selectedCount.value ?: 0
        if (isSelected){
            _selectedCount.value = count + 1
        }
        else{
            _selectedCount.value = count - 1

        }
    }
    fun canSelectMore(): Boolean{
        val count = _selectedCount.value ?:0
        return  count < maxSelections
    }

}