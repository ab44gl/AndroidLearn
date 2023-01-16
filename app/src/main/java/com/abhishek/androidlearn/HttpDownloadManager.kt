package com.abhishek.androidlearn


import android.os.Environment
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class HttpDownloadManger(private val lifecycleCoroutineScope: LifecycleCoroutineScope) :
    DownloadManger {
    private var count = 0
    private var dInfoList: ArrayList<DInfo> = arrayListOf()
    private var onStartCallback: ((info: Info) -> Unit)? = null
    private var onStopCallback: ((info: Info) -> Unit)? = null
    private var onProgressCallback: ((info: Info) -> Unit)? = null
    private var onDeleteCallback: ((info: Info) -> Unit)? = null
    private var onCompleteCallback: ((info: Info) -> Unit)? = null
    override fun addUrl(url: String): Info {

        val info = Info(count, -1)
        val dInfo = DInfo(url, info)
        dInfoList.add(dInfo)
        lifecycleCoroutineScope.launch(Dispatchers.IO) {
            startDownload(dInfo)
        }
        count++
        return info
    }

    private suspend fun startDownload(dInfo: DInfo) {
        //start download

        val bufferSize = 1024 * 10
        val buffer = ByteArray(bufferSize)
        var bytesDownloaded = 0
        var privValue = 0
        val url = URL(dInfo.url);
        val con = if (url.protocol == "http") {
            url.openConnection() as HttpURLConnection
        } else {
            url.openConnection() as HttpsURLConnection
        }
        val totalSizeInBytes = con.contentLength
        val fileName = "${System.currentTimeMillis()}"
        val file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/$fileName.mp4")
        file.createNewFile()
        val fileOutputStream = FileOutputStream(file)
        while (true) {
            val size = read(con.inputStream, buffer, bufferSize)
            // Help.logD(String(buffer))
            if (size == -1) {
                break;
            }
            bytesDownloaded += size
            fileOutputStream.write(buffer, 0, size)
            withContext(Dispatchers.Main) {
                val value = (100 * (bytesDownloaded.toFloat() / totalSizeInBytes.toFloat())).toInt()
                if (privValue != value) {
                    privValue = value
                    dInfo.info.progress = value
                    onProgressCallback?.invoke(dInfo.info)
                }
            }
        }
        fileOutputStream.close()
        Help.logD("total $totalSizeInBytes downloaded $bytesDownloaded ")


    }

    private suspend fun read(
        inputStream: InputStream,
        buffer: ByteArray,
        length: Int
    ): Int {
        return inputStream.read(buffer, 0, length)
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