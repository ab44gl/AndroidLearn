package com.abhishek.androidlearn.animationfragments

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.abhishek.androidlearn.R
import com.abhishek.androidlearn.databinding.FragmentPinAnimationBinding

class PinAnimationFragment : Fragment() {
    private lateinit var binding: FragmentPinAnimationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPinAnimationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
        val imageView = binding.imgvAnim1PinAnimationFrag
        val avd = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.avd_endless_pin_jump)
        val iv = imageView.apply {
            setImageDrawable(avd)
        }
        var  isLoop=true
        avd?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                if(isLoop)iv.post { avd.start() }
            }
        })
        avd?.start()
        imageView.setOnClickListener{
            avd?.let {
                if(it.isRunning){
                    isLoop=false
                    it.stop()
                }
                else {
                    isLoop=true
                    it.start()
                }
            }
        }
    }
}