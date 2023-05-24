package com.izelhatipoglu.videoappfirebase.landing.login


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentDoctorLoginBinding
import com.izelhatipoglu.videoappfirebase.doctor.DoctorActivity
import com.izelhatipoglu.videoappfirebase.landing.login.viewModel.DoctorLoginViewModel

class DoctorLoginFragment : BaseFragment<DoctorLoginViewModel, FragmentDoctorLoginBinding>() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    lateinit var mail : String
    lateinit var password : String

    val LOGIN_TYPE = "login_type"

    override fun getViewModel(): Class<DoctorLoginViewModel>  = DoctorLoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        handleClick()
        observeData()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDoctorLoginBinding = FragmentDoctorLoginBinding.inflate(inflater, container,false)

    private fun handleClick(){
        binding.button.setOnClickListener {
            mail =binding.mail.text.toString()
            password = binding.password.text.toString()

            viewModel.getData(mail)
        }

        binding.signUpBtn.setOnClickListener {
            val action = DoctorLoginFragmentDirections.actionDoctorLoginFragmentToLoginFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun initUI(){
        sharedPreference = requireActivity().getSharedPreferences("com.izelhatipoglu.videoappfirebase",
            Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(requireActivity(),DoctorActivity::class.java))
            activity?.finish()
            val mailPreferences = sharedPreference.getString("mail","")
            val passwordPreferences = sharedPreference.getString("password","")
        }
    }

    private fun observeData(){
        viewModel.loginData.observe(viewLifecycleOwner){ loginData ->
            if(loginData){
                editor.putString(LOGIN_TYPE,"doctor")
                editor.apply()
                startActivity(Intent(requireActivity(),DoctorActivity::class.java))
                activity?.finish()
            }else {
                Toast.makeText(context, "You have entered incorrectly!", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.controldata.observe(viewLifecycleOwner){ controlData ->
            if(controlData){
                viewModel.login(mail,password)

            }
        }

        viewModel.isLoding.observe(viewLifecycleOwner){ isLoading ->
            if(isLoading){
                binding.loginFragment.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.loginFragment.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }

        }
    }
}

