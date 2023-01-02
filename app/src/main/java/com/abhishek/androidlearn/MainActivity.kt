package com.abhishek.androidlearn

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private  lateinit var binding:ActivityMainBinding
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding=ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
       //-------------------------------------
       // background service
        binding.butServiceBackStart.setOnClickListener{
            val intent=Intent(
                this@MainActivity,
                MyBackgroundService::class.java
            )
            val sres=startService(intent)

            Help.log_(sres)
            var res=getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        }
       binding.butServiceBackStop.setOnClickListener{
           val res=stopService(Intent(
               this@MainActivity,
               MyBackgroundService::class.java
           ))
           Help.log_(res)
       }

       // foreground service
       binding.butServiceForeStart.setOnClickListener{
           val  intent=Intent(
               this@MainActivity,
               MyForegroundService::class.java
           )
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               startForegroundService(intent)
           }else{
               startService(intent)
           }

       }
       binding.butServiceForeStop.setOnClickListener{
           val res=stopService(Intent(
               this@MainActivity,
               MyForegroundService::class.java
           ))

           Help.log_(res)
       }
   }
}