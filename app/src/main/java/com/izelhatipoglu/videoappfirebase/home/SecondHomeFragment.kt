package com.izelhatipoglu.videoappfirebase.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentSecondHomeBinding
import com.izelhatipoglu.videoappfirebase.home.viewModel.SecondHomeViewModel

class SecondHomeFragment : BaseFragment<SecondHomeViewModel, FragmentSecondHomeBinding>() {

    override fun getViewModel(): Class<SecondHomeViewModel> = SecondHomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSecondHomeBinding = FragmentSecondHomeBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}