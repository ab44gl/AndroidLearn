package com.abhishek.androidlearn.animationfragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhishek.androidlearn.R
import com.abhishek.androidlearn.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val imageView = binding.imgvAnim1FirstFrag
        val animationDrawable = imageView.drawable as AnimationDrawable
        if (!animationDrawable.isRunning) animationDrawable.start()
        imageView.setOnClickListener {
            if (animationDrawable.isRunning) animationDrawable.stop()
            else animationDrawable.start()
        }
    }
}