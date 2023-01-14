package com.abhishek.androidlearn

import android.util.Log

class Help {
    companion object{
        fun logD(msg: Any?, e: Throwable? = null) {
            Log.d("dell", msg.toString(), e)
        }
    }
}