package com.abhishek.androidlearn

import android.util.Log
import java.util.*

class Help {
    companion object {
        const val TAG = "------------------"
        fun log_(msg: Any?) {
            Log.d(TAG, msg.toString())
        }

        fun dateTime(): String {
            return Date().toString()
        }
    }


}