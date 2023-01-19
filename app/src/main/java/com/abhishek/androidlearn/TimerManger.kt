package com.abhishek.androidlearn

import android.os.Handler
import android.os.Looper

interface Timer {
    fun start()
    fun increment(value: Int)
    fun reset()
    fun stop()
    fun isStarted(): Boolean
    fun setOnTickListener(f: (count: Int) -> Unit)
    fun getCount(): Int
    fun getTotal(): Int
}

class TimerManager(val delay: Int = 1000) {
    private inner class TmTimer(var mTotal: Int = 10) : Timer {
        var mIsStarted = false
        var mCount = 0
        private var tickCallback: ((count: Int) -> Unit)? = null
        override fun start() {
            mIsStarted = true
        }

        override fun increment(value: Int) {
            mTotal += value
        }

        override fun reset() {
            mCount = 0
        }

        override fun stop() {
            mIsStarted = false
        }

        override fun isStarted(): Boolean = mIsStarted

        override fun setOnTickListener(f: (count: Int) -> Unit) {
            tickCallback = f
        }

        override fun getCount(): Int {
            return mCount
        }

        override fun getTotal(): Int {
            return mTotal
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
            handler.postDelayed(object : Runnable {
                override fun run() {
                    updateAllTimer()
                    handler.postDelayed(this, delay.toLong())
                }
            }, delay.toLong())
        }
    }

    private fun updateAllTimer() {
        timers.forEach {
            if (it.mIsStarted) {
                if (it.mTotal == -1) {
                    it.onTick(it.mCount)
                    it.mCount++
                } else {
                    if (it.mCount < it.mTotal) {
                        it.onTick(it.mCount)
                        it.mCount++
                    }
                }
            }

        }
    }
}