package com.abhishek.androidlearn

import android.os.Handler
import android.os.Looper

interface Timer {
    fun start()
    fun increment(value: Int)
    fun resetAndStop()
    fun reset()
    fun pause()
    fun resume()
    fun isResume(): Boolean
    fun isStarted(): Boolean
    fun setOnTickListener(f: (count: Int) -> Unit)
}

class TimerManager(val delay: Int = 1000) {
    private inner class TmTimer(var total: Int = 10) : Timer {
        var mIsResume = false
        var mIsStarted = false
        var count = 0
        private var tickCallback: ((count: Int) -> Unit)? = null
        override fun start() {
            mIsStarted = true
            mIsResume = true
        }

        override fun increment(value: Int) {
            total += value
        }

        override fun resetAndStop() {
            count = 0
            mIsStarted = false
            mIsResume = false
            onTick(0)
        }

        override fun reset() {
            count = 0
        }

        override fun pause() {
            mIsResume = false
        }

        override fun resume() {
            mIsResume = true
        }

        override fun isResume(): Boolean = mIsResume
        override fun isStarted(): Boolean = isStarted

        override fun setOnTickListener(f: (count: Int) -> Unit) {
            tickCallback = f
        }

        fun onTick(count: Int) {
            tickCallback?.invoke(count)
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isStarted = false
    private val timers = arrayListOf<TmTimer>()
    private fun addTimer(timer: TmTimer) {
        timers.add(timer)
        start()
    }

    fun createTimer(total: Int = 10): Timer {
        val timer = TmTimer(total)
        addTimer(timer)
        return timer
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
            if (it.mIsStarted && it.mIsResume) {
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