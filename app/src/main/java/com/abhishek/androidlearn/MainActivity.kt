package com.abhishek.androidlearn

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private  lateinit var binding:ActivityMainBinding
   private  val myDataViewModel:MyDataViewModel by viewModels()
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

       binding=ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
       //-------------------------------------
       myDataViewModel.currentName.observe(this@MainActivity){
           binding.tvMsgAm.text=it
       }
       binding.apply {
           etMsgMact.setOnEditorActionListener { _, actionId, _ ->
               if (actionId == EditorInfo.IME_ACTION_DONE) {
                   myDataViewModel.currentName.value = etMsgMact.text.toString()
                   return@setOnEditorActionListener true;
               }
               return@setOnEditorActionListener false;
           }
           myDataViewModel.currentTime.observe(this@MainActivity) {
               tvTime.text = it
           }
       }
   }
}

