package com.abhishek.androidlearn

import android.os.Handler
import android.os.Looper

class Timer(var total: Int = 10) {
    var isResume = true
    var isStarted = true
    var count = 0
    private var tickCallback: ((count: Int) -> Unit)? = null
    fun start() {
        isStarted = true
        isResume = true
    }

    fun increment(value: Int) {
        total += value
    }

    fun resetAndStop() {
        count = 0
        isStarted = false
        isResume = false
        onTick(0)
    }

    fun reset() {
        count = 0
    }

    fun pause() {
        isResume = false
    }

    fun resume() {
        isResume = true
    }

    fun setOnTickListener(f: (count: Int) -> Unit) {
        tickCallback = f
    }

    fun onTick(count: Int) {
        tickCallback?.invoke(count)
    }
}

class TimerManager(val delay: Int = 1000) {
    private val handler = Handler(Looper.getMainLooper())
    private var isStarted = false
    private val timers = arrayListOf<Timer>()
    fun addTimer(timer: Timer) {
        timers.add(timer)
        start()
    }

    fun removeTimer(timer: Timer) {
        timers.remove(timer)
    }

    private fun start() {
        if (!isStarted) {
            isStarted = true
            var last = System.currentTimeMillis()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    var current = System.currentTimeMillis()
                    updateAllTimer()
                    handler.postDelayed(this, delay.toLong())
                }
            }, delay.toLong())
        }
    }

    private fun updateAllTimer() {
        timers.forEach {
            if (it.isStarted && it.isResume) {
                if (it.total == -1) {
                    it.onTick(it.count)
                    it.count++
                } else {
                    if (it.count < it.total) {
                        it.onTick(it.count)
                        it.count++
                    }
                }
            }

        }
    }
}