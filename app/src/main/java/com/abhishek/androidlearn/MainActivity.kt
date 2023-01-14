package com.abhishek.androidlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.androidlearn.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecycleViewProgressAdapter
    private val infoList = MutableLiveData(Info.randomList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------
        adapter = RecycleViewProgressAdapter()
        infoList.observe(this) {
            adapter.submitList(it)
        }
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch(Dispatchers.Default) {
            updateList()
        }

    }

    suspend fun updateList() {
        while (true) {
            delay(1000)
            withContext(Dispatchers.Main) {
                infoList.value = Info.randomList()
            }
        }
    }
}