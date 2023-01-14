package com.abhishek.androidlearn

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class LiveDataViewModel(
    private val dataSource: DataSource
) : ViewModel() {
    val currentTime = dataSource.getCurrentTime()

    //coroutine inside a transformation
    val currentTimeTransformed = currentTime.switchMap {
        //timeStampToTime is a suspend fun so we need to call inside coroutine
        liveData { emit(timeStampToTime(it)) }
    }

    //exposed liveData that emits and single value and subsequent values from another source
    val currentWeather = liveData {
        emit(LOADING_STRING)
        emitSource(dataSource.fetchWeather())
    }

    //exposed cache value in data source that can be updated later on
    val cacheValue = dataSource.cachedData
    fun refresh() {
        //launch a coroutine that reads from remote data source and updates cache value
        viewModelScope.launch {
            dataSource.fetchNewData()
        }
    }


    private suspend fun timeStampToTime(timeStamp: Long): String {
        delay(500)
        val date = Date(timeStamp)
        return date.toString()
    }

    companion object {
        const val LOADING_STRING = "Loading..."
    }
}

object LiveDataVMFactory : ViewModelProvider.Factory {
    private val dataSource = DefaultDataSource()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LiveDataViewModel(dataSource) as T
    }
}