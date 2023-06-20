package com.izelhatipoglu.videoappfirebase.doctor.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentHomeDoctorBinding
import com.izelhatipoglu.videoappfirebase.doctor.info.DoctorInfoActivity
import com.izelhatipoglu.videoappfirebase.doctor.adapter.MyDoctorAdapter
import com.izelhatipoglu.videoappfirebase.doctor.home.viewModel.HomeDoctorViewModel
import com.izelhatipoglu.videoappfirebase.home.HomeFragmentDirections
import com.izelhatipoglu.videoappfirebase.landing.LandingActivity
import com.izelhatipoglu.videoappfirebase.model.DoctorSickList

class HomeDoctorFragment : BaseFragment<HomeDoctorViewModel,FragmentHomeDoctorBinding>() {

    private lateinit var myDoctorAdapter: MyDoctorAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun getViewModel(): Class<HomeDoctorViewModel> = HomeDoctorViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeDoctorBinding = FragmentHomeDoctorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDoctorName()
        observeData()
        handleClick()

        myDoctorAdapter = MyDoctorAdapter(arrayListOf(), object : MyDoctorAdapter.IsClickedDoctor {
            override fun clickedDoctor(doctorSickList: DoctorSickList) {

                val b = Bundle()
                b.putSerializable("doctorSickList", doctorSickList)
                val intent = Intent(requireActivity(), DoctorInfoActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                activity?.finish()
                // Sick Id burda gönderilcek diğer fragmenta
                
            }

        })
        binding.recylerView.layoutManager = LinearLayoutManager(context)
        binding.recylerView.adapter = myDoctorAdapter

        viewModel.getData()

    }

    private fun handleClick(){
        binding.floatingBtn.setOnClickListener {
            val action = HomeDoctorFragmentDirections.actionHomeDoctorFragmentToAddVideoFragment()
            NavHostFragment.findNavController(this@HomeDoctorFragment).navigate(action)
        }
        binding.logOutBtn.setOnClickListener{
            auth.signOut()
            startActivity(Intent(requireActivity(), LandingActivity::class.java))
            activity?.finish()
        }
    }

    private fun observeData() {
        viewModel.doctorName.observe(viewLifecycleOwner){name ->
            name?.let {
                binding.tvName.text = name
            }
        }
        viewModel.sickList.observe(viewLifecycleOwner) { sickList ->

           myDoctorAdapter.updateListDoctor(sickList)


        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.homeDoctorLayout.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.homeDoctorLayout.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }

        }

    }

}

