package com.abhishek.androidlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private val infoList = arrayListOf<Info>()
    private val fakeDownloadManger = FakeDownloadManger(lifecycleScope)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------
        adapter = RecycleViewProgressAdapter(infoList)

        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(this)

        infoList.add(fakeDownloadManger.addUrl(""))
        lifecycleScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                infoList.add(fakeDownloadManger.addUrl(""))
            }
        }
        fakeDownloadManger.setOnProgress {
            adapter.updateInfo(it)
        }
        fakeDownloadManger.setOnComplete {
            Help.logD("completed $it")
        }


    }


}