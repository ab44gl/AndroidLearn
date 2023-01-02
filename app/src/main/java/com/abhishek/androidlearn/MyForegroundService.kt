package com.abhishek.androidlearn


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder


class MyForegroundService : Service() {
    var isRun=false
    val CHANNELID = "Foreground Service ID"
    override fun onCreate() {
        Help.log_("MyForegroundService  OnCreate")
        super.onCreate()
        isRun=true
        Thread{
            while (isRun){
                Help.log_("MyForegroundService ${Thread.currentThread().id} >hello")
                Thread.sleep(1000)
            }
            Help.log_("thread MyForegroundService  closed")
        }.apply {
            start()
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
            val notification = Notification.Builder(this, CHANNELID)
                .setContentText("Service is running")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
            startForeground(10001,notification.build())
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Help.log_("MyForegroundService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
  

    override fun onDestroy() {
        isRun=false
        Help.log_("MyForegroundService  onDestroy")
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}