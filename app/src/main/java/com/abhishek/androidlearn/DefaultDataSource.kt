package com.abhishek.androidlearn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DefaultDataSource : DataSource {
    override fun getCurrentTime(): LiveData<Long> = liveData {
        while (true) {
            emit(System.currentTimeMillis())
            delay(500)
        }
    }

    private val weatherConditions = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Snowy")

    override fun fetchWeather(): LiveData<String> = liveData {
        var count = 0
        while (true) {
            count++
            delay(2000)
            emit(weatherConditions[count % weatherConditions.size])
        }
    }

    /*
    * Cache + Remote data source
    * */
    private val _cachedData = MutableLiveData("old data")
    override val cachedData: LiveData<String> = _cachedData

    //called when cache need to refresh
    override suspend fun fetchNewData() {
        //force main thread
        withContext(Dispatchers.Main) {
            _cachedData.value = "Fetching new data...."
            _cachedData.value = simulateNetworkDataFetch()
        }
    }

    //fetch new data in background.must be called from coroutine
    private var count = 0
    private suspend fun simulateNetworkDataFetch(): String = withContext(Dispatchers.IO) {
        delay(3000)
        count++
        "New data from request #$count"
    }

}

interface DataSource {
    fun getCurrentTime(): LiveData<Long>
    fun fetchWeather(): LiveData<String>
    val cachedData: LiveData<String>
    suspend fun fetchNewData()
}