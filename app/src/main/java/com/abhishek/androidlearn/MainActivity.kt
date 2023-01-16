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
    private val url =
        "http://192.168.43.1:12345/Download%2F05.Mobile+%26+Web+Design+UI+Colour+Systems+%26+Palette+_+UX+Design+System+Tutorial+_+Ansh+Mehra+UX+Designer.mp4"
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecycleViewProgressAdapter
    private val infoList = arrayListOf<Info>()
    private val httpDownloadManger = HttpDownloadManger(lifecycleScope)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------
        adapter = RecycleViewProgressAdapter(infoList)

        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(this)

        infoList.add(httpDownloadManger.addUrl(url))
        lifecycleScope.launch {
            repeat(10) {
                delay(100)
                withContext(Dispatchers.Main) {
                    infoList.add(httpDownloadManger.addUrl(url))
                }
            }
        }
        httpDownloadManger.setOnProgress {
            adapter.updateInfo(it)
        }
        httpDownloadManger.setOnComplete {
            Help.logD("completed $it")
        }
        binding.floatingActionButton.setOnClickListener {

            try {
                lifecycleScope.launch(Dispatchers.IO) {

                }
            } catch (e: Exception) {
                Help.logD("error", e)
            }

        }


    }


}