package com.abhishek.androidlearn

import android.util.Log

class Help {
    companion object{
        fun log_(msg: Any) {
            Log.e("---------------",msg.toString())
        }
    }
}