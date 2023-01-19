package com.abhishek.androidlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RvAdapter
    private lateinit var timerManager: TimerManager
    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private val timers = arrayListOf<Timer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------
        timerManager = TimerManager(500)
        binding.floatingActionButton.setOnClickListener {
            this.addNewTimer()
            Help.logd("${timers.size}")
        }
        this.adapter = RvAdapter(timers)
        addNewTimer()
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter
        adapter.setOnItemClick { p, msg ->
            if (msg == "resetStop") {
                timers[p].apply {
                    stop()
                    reset()
                }
            } else {
                timers[p].apply {
                    if (isStarted()) stop() else start()
                }
            }
            adapter.update()
        }
    }

    private fun addNewTimer() {
        val timer = timerManager.createTimer(-1)
        timers.add(timer)
        timer.start()
        timer.setOnTickListener {
            adapter.update()
        }
        adapter.update()
    }

}