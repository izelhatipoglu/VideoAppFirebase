package com.izelhatipoglu.videoappfirebase.landing.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentLoginBinding
import com.izelhatipoglu.videoappfirebase.home.HomeActivity
import com.izelhatipoglu.videoappfirebase.landing.login.viewModel.LoginViewModel

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        handleClick()
        observeData()
    }

    private fun initUI(){
        sharedPreference = requireActivity().getSharedPreferences("com.izelhatipoglu.videoappfirebase",Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        val currentUser = auth.currentUser
        if (currentUser != null){

            //daha önce giriş yaptı direkt home gidiyor
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            activity?.finish()
            val mailPreferences = sharedPreference.getString("KEY_MAIL","")
            val passwordPreferences = sharedPreference.getString("KEY_PASSWORD","")


        }

    }

    private fun handleClick() {
        binding.button.setOnClickListener {
            val mail = binding.mail.text.toString()
            val password = binding.password.text.toString()
            viewModel.login(mail, password)
        }

        binding.signUpBtn.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun observeData() {
        viewModel.loginData.observe(viewLifecycleOwner) { loginData ->
            if (loginData) {
                // firebase collectiona bilgileri eklicem burda
            } else {
                Toast.makeText(context, "You have entered incorrectly!", Toast.LENGTH_SHORT).show()
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
