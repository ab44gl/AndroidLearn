package com.abhishek.androidlearn

import android.util.Log

class Help {
    companion object{
        fun logd(msg: Any?, e: Throwable? = null) {
            Log.d("dell", msg.toString(), e)
        }
    }
}