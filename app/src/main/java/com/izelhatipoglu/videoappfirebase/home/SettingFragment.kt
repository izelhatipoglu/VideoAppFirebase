package com.izelhatipoglu.videoappfirebase.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentSettingBinding
import com.izelhatipoglu.videoappfirebase.home.viewModel.SettingViewModel

class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {

    override fun getViewModel(): Class<SettingViewModel> = SettingViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSettingBinding = FragmentSettingBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}