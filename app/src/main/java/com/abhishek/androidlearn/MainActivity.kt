package com.abhishek.androidlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhishek.androidlearn.animationfragments.FirstFragment
import com.abhishek.androidlearn.animationfragments.PinAnimationFragment
import com.abhishek.androidlearn.animationfragments.SecondFragment
import com.abhishek.androidlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private  lateinit var binding:ActivityMainBinding
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding=ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
       //-------------------------------------
       val fragments= arrayListOf<Fragment>(
           FirstFragment(),
           SecondFragment(),
           PinAnimationFragment(),
       )
       val adapter=ViewPagerAdapter(fragments,this)
       binding.viewPager.adapter=adapter
   }
}

class ViewPagerAdapter(
    private val list:ArrayList<Fragment>,
    activity: MainActivity
):FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return  list[position]
    }

}