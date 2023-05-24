package com.izelhatipoglu.videoappfirebase.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentDoctorInfoBinding
import com.izelhatipoglu.videoappfirebase.doctor.viewModel.DoctorInfoViewModel

class DoctorInfoFragment :BaseFragment<DoctorInfoViewModel, FragmentDoctorInfoBinding>(){

    override fun getViewModel(): Class<DoctorInfoViewModel> = DoctorInfoViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ):FragmentDoctorInfoBinding = FragmentDoctorInfoBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}