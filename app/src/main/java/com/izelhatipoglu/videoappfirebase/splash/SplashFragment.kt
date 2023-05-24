package com.izelhatipoglu.videoappfirebase.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentSplashBinding
import com.izelhatipoglu.videoappfirebase.doctor.DoctorActivity
import com.izelhatipoglu.videoappfirebase.home.HomeActivity
import com.izelhatipoglu.videoappfirebase.landing.LandingActivity
import com.izelhatipoglu.videoappfirebase.splash.viewModel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    private val activityScope = CoroutineScope(Dispatchers.Main)

    private val auth = FirebaseAuth.getInstance()
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    val LOGIN_TYPE = "login_type"

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

            val currentUser = auth.currentUser
            if (currentUser != null){
                controlCurrentUserType()
            }else{
                goToLanding()
            }
        }
    }

    private fun goToLanding(){
        startActivity(Intent(requireActivity(), LandingActivity::class.java))
        activity?.finish()
    }

    private fun controlCurrentUserType(){
        sharedPreference = requireActivity().getSharedPreferences("com.izelhatipoglu.videoappfirebase",
            Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

            //daha önce giriş yaptı direkt home gidiyor
            val mailPreferences = sharedPreference.getString("mail","")
            val passwordPreferences = sharedPreference.getString("password","")
            val typePreferences = sharedPreference.getString("login_type","")
        println(" SHARED : $typePreferences")
            when(typePreferences){
                "sick" ->{
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    activity?.finish()
                }
                "doctor"->{
                    startActivity(Intent(requireActivity(), DoctorActivity::class.java))
                    activity?.finish()
                }
            }


    }



}