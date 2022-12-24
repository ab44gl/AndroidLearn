package com.abhishek.androidlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private  lateinit var binding:ActivityMainBinding
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding=ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)       //-------------------------------------
       val bottomNavigationView=binding.bottomNavigationView
       val navController=binding.fragmentContainerView.getFragment<NavHostFragment>().navController
       val appBarConfiguration=AppBarConfiguration(setOf(R.id.firstFragment,R.id.secondFragment,R.id.thirdFragment))
       setupActionBarWithNavController(navController,appBarConfiguration)
       bottomNavigationView.setupWithNavController(navController)

   }
}