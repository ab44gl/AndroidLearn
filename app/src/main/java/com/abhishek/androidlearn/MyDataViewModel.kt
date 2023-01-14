package com.abhishek.androidlearn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.delay
import java.util.*

class MyDataViewModel : ViewModel() {
    var count = 100
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val currentTime = liveData<String> {
        while (true) {
            emit(Calendar.getInstance().time.toString())
            delay(500)
        }
    }
    val currentTimeTransform = currentTime.switchMap {
        liveData { emit(transformTime(it)) }
    }

    private suspend fun transformTime(time: String): String {
        //fake that it takes time
        delay(1000)
        return time.subSequence(0, 5).toString()
    }

}