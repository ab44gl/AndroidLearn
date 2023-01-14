package com.abhishek.androidlearn

interface DownloadManger {
    fun addUrl(url: String): Info
    fun resume(info: Info)
    fun delete(info: Info)
    fun pause(info: Info)

    fun setOnStop(f: (info: Info) -> Unit)
    fun setOnStart(f: (info: Info) -> Unit)
    fun setOnProgress(f: (info: Info) -> Unit)
    fun setOnDelete(f: (info: Info) -> Unit)
    fun setOnComplete(f: (info: Info) -> Unit)
}