package com.abhishek.androidlearn

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Help {
    companion object {
        fun log_(msg: Any?, e: Throwable? = null) {
            Log.d("dell", msg.toString(), e)
        }

        //method to convert your text to image
        fun textAsBitmap(
            text: String = "png",
            textSize: Float = 20f,
            textColor: Int = Color.BLACK
        ): Bitmap? {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.textSize = textSize
            paint.color = textColor
            paint.textAlign = Paint.Align.LEFT
            val baseline: Float = -paint.ascent() // ascent() is negative
            val width = (paint.measureText(text) + 0.0f).toInt() // round
            val height = (baseline + paint.descent() + 0.0f).toInt()
            val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(image)
            canvas.drawText(text, 0f, baseline, paint)
            return image
        }
    }
}

fun AppCompatActivity.showMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}