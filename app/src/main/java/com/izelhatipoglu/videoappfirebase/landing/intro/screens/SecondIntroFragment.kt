package com.izelhatipoglu.videoappfirebase.landing.intro.screens

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentIntroSecondBinding
import com.izelhatipoglu.videoappfirebase.home.HomeActivity
import com.izelhatipoglu.videoappfirebase.landing.LandingActivity
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.viewModel.SecondIntroViewModel
import com.izelhatipoglu.videoappfirebase.videoPlayer.VideoPlayerActivity

class SecondIntroFragment : BaseFragment<SecondIntroViewModel, FragmentIntroSecondBinding>() {


    override fun getViewModel(): Class<SecondIntroViewModel> = SecondIntroViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentIntroSecondBinding  = FragmentIntroSecondBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager= activity?.findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.currentItem = 2

        binding.tvSkip.setOnClickListener {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            activity?.finish()
        }
    }
}
