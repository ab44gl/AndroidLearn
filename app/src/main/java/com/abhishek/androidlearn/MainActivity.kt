package com.abhishek.androidlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
       //-------------------------------------
       val timerManager = TimerManager(500)
       val timer1 = Timer(50)
       binding.apply {
           msg.setOnClickListener {
               if (timer1.isResume) timer1.pause() else timer1.resume()
           }
           timer1.setOnTickListener {
               msg.text = "$it"
           }
           root.setOnClickListener {
               timer1.reset()
           }
       }
       timerManager.addTimer(timer1)
   }
}