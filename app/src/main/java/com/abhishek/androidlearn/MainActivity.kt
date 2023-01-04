package com.abhishek.androidlearn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abhishek.androidlearn.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private  lateinit var binding:ActivityMainBinding
    private lateinit var signInHelper: GoogleSignInHelper
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding=ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
       //-------------------------------------
        signInHelper= GoogleSignInHelper(this){
            updateUI(it)
        }
       binding.imgButtonSignIn.setOnClickListener{
          signInHelper.signIn()
       }
   }

    override fun onStart() {
        super.onStart()
        if(signInHelper.isAlreadySignIn()){
            signInHelper.signIn()
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
       if(account!=null){
           binding.imgButtonSignIn.visibility= View.GONE
           binding.tvLoginStatus.text=resources.getText(R.string.sign_success)
           //update detail
           binding.tvMsg.text=GoogleSignInHelper.accountString(account)
           lifecycleScope.launch {
               delay(1000)
               val intent=Intent(this@MainActivity,GDriveActivity::class.java)
               startActivity(intent)
           }
       }else{
           binding.imgButtonSignIn.visibility= View.VISIBLE
           binding.tvLoginStatus.text=resources.getText(R.string.sign_failed)
       }
    }
}