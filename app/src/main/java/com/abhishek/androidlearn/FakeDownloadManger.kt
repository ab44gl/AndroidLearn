package com.abhishek.androidlearn

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class DInfo(
    var url: String,
    var info: Info,
    var sizeInBytes: Long = -1,
    var bytesDownloaded: Long = -1,
    var isResume: Boolean = true,
    var isDeleted: Boolean = false
) {}

class FakeDownloadManger(val lifecycleCoroutineScope: LifecycleCoroutineScope) : DownloadManger {
    init {

    }

    private var count = 0
    private var dInfoList: ArrayList<DInfo> = arrayListOf()
    private var onStartCallback: ((info: Info) -> Unit)? = null
    private var onStopCallback: ((info: Info) -> Unit)? = null
    private var onProgressCallback: ((info: Info) -> Unit)? = null
    private var onDeleteCallback: ((info: Info) -> Unit)? = null
    private var onCompleteCallback: ((info: Info) -> Unit)? = null
    override fun addUrl(url: String): Info {
        count++
        val info = Info(count, -1)
        val dInfo = DInfo(url, info)
        dInfoList.add(dInfo)
        lifecycleCoroutineScope.launch(Dispatchers.IO) {
            startDownload(dInfo)
        }
        return info
    }

    private suspend fun startDownload(dInfo: DInfo) {
        //start download
        val totalSizeInBytes = 1024 * 100
        val bufferSize = 1024
        var bytesDownloaded = 0
        //send onStart
        withContext(Dispatchers.Main) {
            onStartCallback?.invoke(dInfo.info)
        }
        while (bytesDownloaded < totalSizeInBytes) {
            if (dInfo.isDeleted) {
                onDeleteCallback?.invoke(dInfo.info)
                return
            }
            if (dInfo.isResume) {
                val len = read(ByteArray(10), 0, bufferSize)
                bytesDownloaded += len
                if (bytesDownloaded >= totalSizeInBytes) {
                    bytesDownloaded = totalSizeInBytes
                }
                withContext(Dispatchers.Main) {
                    dInfo.info.progress =
                        (100 * (bytesDownloaded.toFloat() / totalSizeInBytes.toFloat())).toInt()
                    onProgressCallback?.invoke(dInfo.info)
                }
            } else {
                delay(2000)
            }
        }
        //send onCompleted
        withContext(Dispatchers.Main) {
            onCompleteCallback?.invoke(dInfo.info)
        }

    }

    private suspend fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        delay(Random.nextInt(1, 500).toLong())
        return Random.nextInt((length * 0.5).toInt(), length)
    }

    override fun resume(info: Info) {
        TODO("Not yet implemented")
    }

    override fun delete(info: Info) {
        TODO("Not yet implemented")
    }

    override fun pause(info: Info) {
        TODO("Not yet implemented")
    }

    override fun setOnStop(f: (info: Info) -> Unit) {
        onStopCallback = f
    }

    override fun setOnStart(f: (info: Info) -> Unit) {
        onStartCallback = f
    }

    override fun setOnProgress(f: (info: Info) -> Unit) {
        onProgressCallback = f
    }

    override fun setOnDelete(f: (info: Info) -> Unit) {
        onDeleteCallback = f
    }

    override fun setOnComplete(f: (info: Info) -> Unit) {
        onCompleteCallback = f
    }

}