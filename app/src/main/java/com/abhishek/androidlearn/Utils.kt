package com.abhishek.androidlearn

import java.net.URL
import java.net.URLConnection

class Utils {
    companion object {
        fun nameFromUrl(url: URL): String {
            val pathString = url.path
            if (pathString.isNotEmpty()) {
                val fileName: String =
                    pathString.substring(pathString.lastIndexOf('/') + 1, pathString.length)
                val fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'))
                return fileNameWithoutExtn
            }
            return url.host.replace(".", "_")

        }

        fun fileTypeFromUrlConn(con: URLConnection): String {
            val type = con.contentType
            if (type.contains("text", ignoreCase = true)) return "txt"
            if (type.contains("png", ignoreCase = true)) return "png"
            return "bin"
        }
    }
}