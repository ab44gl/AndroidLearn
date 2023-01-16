package com.abhishek.androidlearn

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.abhishek.androidlearn.databinding.ActivityMainBinding
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_SAVE_PNG = 100
    private lateinit var binding: ActivityMainBinding
    private var color = MutableLiveData(Color.WHITE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            colorView.showText(false)
            tvColorChooser.setOnClickListener {
                val dialog = ColorDialogFragment()
                color.value?.let { it1 -> dialog.setColor(it1) }
                dialog.setOnClickListener { isOk, rgb ->
                    if (isOk) {
                        color.value = rgb
                    }
                }
                dialog.show(supportFragmentManager, null)
            }
            //set png image to fab
            floatingActionButton.setImageBitmap(Help.textAsBitmap())
            floatingActionButton.setOnClickListener {
                savePng()
            }
        }
        color.observe(this) {
            binding.colorView.setColor(it)
        }
    }

    private fun savePng() {
        if (checkPermission()) {
            val filename = if (color.value != null) {
                "${Color.red((color.value!!))}_${Color.green((color.value!!))}_${Color.blue((color.value!!))}"
            } else
                color.toString()
            val dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/$filename.png")
            dir.createNewFile()
            try {
                val out = FileOutputStream(dir)
                val bitmap = binding.colorView.getBitmap()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                showMessage("file save at ${dir.absolutePath}")
            } catch (e: IOException) {
                Help.log_("can't save file", e)
                showMessage("some error while saving file")
            }
            Help.log_(dir.absolutePath)
        }
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_SAVE_PNG
            );
        } else {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_SAVE_PNG -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //granted
                    savePng()
                } else {
                    //explain the user
                }
                return
            }
            else -> {

            }
        }
    }


}

