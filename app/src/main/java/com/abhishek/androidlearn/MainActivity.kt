package com.abhishek.androidlearn

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LiveDataViewModel by viewModels { LiveDataVMFactory }
    var currentTime = ""
    var transformTime = ""
    var emitPlusEmitSource = ""
    var cacheRemote = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------
        binding.apply {
            buttonRefresh.setOnClickListener {
                viewModel.refresh()
            }
        }
        viewModel.currentTime.observe(this) {
            currentTime = "$it"
            updateMessage()
        }
        viewModel.currentTimeTransformed.observe(this) {
            transformTime = "$it"
            updateMessage()
        }
        viewModel.currentWeather.observe(this) {
            emitPlusEmitSource = "$it"
            updateMessage()
        }
        viewModel.cacheValue.observe(this) {
            cacheRemote = "$it"
            updateMessage()
        }
    }

    private fun updateMessage() {

        binding.tvMsg.text =
            "Time\n" +
                    "$currentTime\n" +
                    "Transform Result\n" +
                    "$transformTime\n" +
                    "emit + emitSource\n" +
                    "$emitPlusEmitSource\n" +
                    "Cache + Remote data source\n" +
                    "$cacheRemote"
    }
}

