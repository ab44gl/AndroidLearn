package com.abhishek.androidlearn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyDataViewModel : ViewModel() {
    var count=100
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}