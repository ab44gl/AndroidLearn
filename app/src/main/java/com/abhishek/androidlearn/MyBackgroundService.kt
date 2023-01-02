package com.abhishek.androidlearn

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class MyBackgroundService : Service() {

    var isRun=false
    override fun onCreate() {
        Help.log_("MyBackgroundService  OnCreate")

        super.onCreate()



        isRun=true
        Thread{
            while (isRun){
                Help.log_("service ${Thread.currentThread().id} >hello")
                Thread.sleep(1000)
            }
            Help.log_("thread service  closed")
        }.apply {
            start()
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Help.log_("MyBackgroundService  onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        isRun=false
        Help.log_("MyBackgroundService  onDestroy")
        super.onDestroy()
    }
}