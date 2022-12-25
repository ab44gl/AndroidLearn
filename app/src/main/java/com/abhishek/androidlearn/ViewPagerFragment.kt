package com.abhishek.androidlearn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.abhishek.androidlearn.databinding.FragmentFruitBinding
import com.abhishek.androidlearn.databinding.FragmentViewpagerBinding
import com.abhishek.androidlearn.fruits.FruitFragment


class ViewPagerFragment : Fragment() {
  private lateinit  var binding: FragmentViewpagerBinding
  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
      binding = FragmentViewpagerBinding.inflate(inflater, container, false)
      val fragmentList= arrayListOf<Fragment>(
          FruitFragment("Fruits",getColor(requireContext(), R.color.fruits)),
          FruitFragment("Mango",getColor(requireContext(), R.color.mango)),
          FruitFragment("Litchi",getColor(requireContext(), R.color.litchi)),
          FruitFragment("Apple",getColor(requireContext(), R.color.green_apple)),
      )

      val adapter=ViewPageAdapter(
          fragmentList,
          requireActivity().supportFragmentManager,
          lifecycle
      )
      binding.viewPager.adapter=adapter
      //get parent activity
      val activity=(requireActivity() as MainActivity )
      binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
          override fun onPageSelected(position: Int) {
              super.onPageSelected(position)
              activity.setBottomNavMenuTitle(R.id.viewPagerFragment,(fragmentList[position] as FruitFragment).text)
              //set the title of bottom nav bar
          }
      })
      return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
  }
}

class  ViewPageAdapter(
    private val list:ArrayList<Fragment>,
    fm:FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fm,lifecycle){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}