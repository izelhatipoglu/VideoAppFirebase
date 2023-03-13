package com.izelhatipoglu.videoappfirebase.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentHomeBinding
import com.izelhatipoglu.videoappfirebase.databinding.FragmentInfoHomeBinding
import com.izelhatipoglu.videoappfirebase.home.viewModel.InfoHomeViewModel

class InfoHomeFragment : BaseFragment<InfoHomeViewModel, FragmentHomeBinding>() {

    override fun getViewModel(): Class<InfoHomeViewModel> = InfoHomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}