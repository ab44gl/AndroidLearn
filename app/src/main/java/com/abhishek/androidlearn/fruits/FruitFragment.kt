package com.abhishek.androidlearn.fruits

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.abhishek.androidlearn.R
import com.abhishek.androidlearn.databinding.FragmentFruitBinding


class FruitFragment( val text:String,val color:Int) : Fragment() {
    private lateinit  var binding: FragmentFruitBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFruitBinding.inflate(inflater, container, false)
        //set name and color
        binding.tvNameFragfruit.text=text
        binding.root.setBackgroundColor(color)
        val viewPager2=requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        binding.tvNextFragFruits.setOnClickListener {
            viewPager2.adapter?.let {
                if(it.itemCount-1==viewPager2.currentItem){
                    viewPager2.currentItem=0
                }else{
                    viewPager2.currentItem+=1
                }
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}