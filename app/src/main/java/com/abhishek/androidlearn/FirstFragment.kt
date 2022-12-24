package com.abhishek.androidlearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.abhishek.androidlearn.databinding.FragmentFirstBinding
class FirstFragment : Fragment() {
   private lateinit  var binding: FragmentFirstBinding
   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
       binding = FragmentFirstBinding.inflate(inflater, container, false).apply {
           tvMsgFirstFrag.setOnClickListener {
               //goto second fragment
               val action=FirstFragmentDirections.navigateToSecond(200)
               Navigation.findNavController(binding.root).navigate(action)
           }
       }
       return binding.root
   }
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
   }
}