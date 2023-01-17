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
        val url = URL(dInfo.url);
        val con = if (url.protocol == "http") {
            url.openConnection() as HttpURLConnection
        } else {
            url.openConnection() as HttpsURLConnection
        }
        val totalSizeInBytes = con.contentLength

        val fileName = Utils.nameFromUrl(url)
        val ext = Utils.fileTypeFromUrlConn(con)
        val file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/$fileName.$ext")
        file.createNewFile()
        val fileOutputStream = FileOutputStream(file)
        var lastTime = System.currentTimeMillis()
        while (true) {

            val size = read(con.inputStream, buffer, bufferSize)
            if (size == -1) break
            bytesDownloaded += size
            fileOutputStream.write(buffer, 0, size)
            var currentTime = System.currentTimeMillis()
            val diffTimeSecond = (currentTime - lastTime).toFloat() / 1000f
            var sizeInKB = size.toFloat() / (1024f * 1024f)
            var rate = sizeInKB / diffTimeSecond
            if (totalSizeInBytes > 0) {
                withContext(Dispatchers.Main) {
                    val value =
                        (100 * (bytesDownloaded.toFloat() / totalSizeInBytes.toFloat())).toInt()
                    dInfo.info.progress = value
                    dInfo.info.rate = rate
                    onProgressCallback?.invoke(dInfo.info)
                }
            }
            lastTime = currentTime


        }
        fileOutputStream.close()
        Help.logD("total $totalSizeInBytes downloaded $bytesDownloaded ")
        if (totalSizeInBytes < 1) {
            withContext(Dispatchers.Main) {
                dInfo.info.progress = 100
                onProgressCallback?.invoke(dInfo.info)
            }
        }


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