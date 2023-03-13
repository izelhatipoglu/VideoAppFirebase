package com.izelhatipoglu.videoappfirebase.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentSplashBinding
import com.izelhatipoglu.videoappfirebase.landing.LandingActivity
import com.izelhatipoglu.videoappfirebase.splash.viewModel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCoroutineScope()
    }

    private fun initCoroutineScope(){
        activityScope.launch {
            delay(2000)
            goToLanding()
        }
    }

    private fun goToLanding(){
        startActivity(Intent(requireActivity(), LandingActivity::class.java))
        activity?.finish()
    }


}