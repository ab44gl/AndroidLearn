package com.abhishek.androidlearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.abhishek.androidlearn.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {
 private lateinit  var binding: FragmentSecondBinding
 val args:SecondFragmentArgs by navArgs()
 override fun onCreateView(
     inflater: LayoutInflater,
     container: ViewGroup?,
     savedInstanceState: Bundle?
 ): View {
     binding = FragmentSecondBinding.inflate(inflater, container, false)
     //---------------------
     val total=args.total
     binding.apply {
         tvMsgFragSec.text=total.toString()
         tvMsgFragSec.setOnClickListener {
             //goto second fragment
             Navigation.findNavController(binding.root).navigate(R.id.navigateToFirst)
         }
     }

     return binding.root
 }
 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     super.onViewCreated(view, savedInstanceState)
 }
}