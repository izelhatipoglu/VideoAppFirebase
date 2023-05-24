package com.izelhatipoglu.videoappfirebase.doctor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentHomeBinding
import com.izelhatipoglu.videoappfirebase.databinding.FragmentHomeDoctorBinding
import com.izelhatipoglu.videoappfirebase.doctor.adapter.MyDoctorAdapter
import com.izelhatipoglu.videoappfirebase.doctor.viewModel.HomeDoctorViewModel
import com.izelhatipoglu.videoappfirebase.model.DoctorSickList

class HomeDoctorFragment : BaseFragment<HomeDoctorViewModel,FragmentHomeDoctorBinding>() {


    private lateinit var myDoctorAdapter: MyDoctorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDoctorAdapter = MyDoctorAdapter(arrayListOf(),object :MyDoctorAdapter.IsClickedDoctor{
            override fun clickedDoctor(doctorSickList: DoctorSickList) {

                // Sick Id burda gönderilcek diğer fragmenta
                val action = HomeDoctorFragmentDirections.actionHomeDoctorFragmentToDoctorInfoFragment()
                NavHostFragment.findNavController(this@HomeDoctorFragment).navigate(action)
            }

        })
        binding.recylerView.layoutManager = LinearLayoutManager(context)
        binding.recylerView.adapter = myDoctorAdapter


    }

    override fun getViewModel(): Class<HomeDoctorViewModel> = HomeDoctorViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeDoctorBinding = FragmentHomeDoctorBinding.inflate(inflater, container, false)

private fun observeData(){

}

}