package com.izelhatipoglu.videoappfirebase.landing.intro.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentFirstIntroBinding
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.viewModel.FirstIntroViewModel

class FirstIntroFragment : BaseFragment<FirstIntroViewModel, FragmentFirstIntroBinding>(){

    override fun getViewModel(): Class<FirstIntroViewModel>  = FirstIntroViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFirstIntroBinding = FragmentFirstIntroBinding.inflate(inflater,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager= activity?.findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.currentItem = 1
    }
}