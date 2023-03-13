package com.izelhatipoglu.videoappfirebase.landing.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentIntroBinding
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.FirstIntroFragment
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.SecondIntroFragment
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.viewModel.FirstIntroViewModel
import com.izelhatipoglu.videoappfirebase.landing.intro.viewModel.IntroViewModel


class IntroFragment : BaseFragment<IntroViewModel, FragmentIntroBinding>() {

    override fun getViewModel(): Class<IntroViewModel>  =IntroViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentIntroBinding = FragmentIntroBinding.inflate(inflater,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            FirstIntroFragment(),
            SecondIntroFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle = lifecycle
        )
        binding.viewPager.adapter = adapter

        binding.dot.attachTo(binding.viewPager)
    }

}