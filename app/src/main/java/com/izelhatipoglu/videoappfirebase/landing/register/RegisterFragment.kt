package com.izelhatipoglu.videoappfirebase.landing.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentRegisterBinding
import com.izelhatipoglu.videoappfirebase.home.HomeActivity
import com.izelhatipoglu.videoappfirebase.landing.register.viewModel.RegisterViewModel
import java.util.*

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {

    private lateinit var sharedPreference : SharedPreferences
    private lateinit var  editor : SharedPreferences.Editor

    val KEY_MAIL = "mail"
    val KEY_PASSWORD = "password"



    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = requireActivity().getSharedPreferences("com.izelhatipoglu.videoappfirebase",Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        handleClick()
        observeData()
    }

    private fun handleClick(){
        binding.button.setOnClickListener {
            val mail = binding.mail.text.toString()
            val password = binding.password.text.toString()
            val name = binding.name.text.toString()
            viewModel.register(mail,password,name)
        }
        binding.loginBt.setOnClickListener{
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

    }

    private fun observeData() {
        viewModel.isRegister.observe(viewLifecycleOwner) { isRegister ->
            if (isRegister) {
                Toast.makeText(context, "Registered in the system", Toast.LENGTH_SHORT).show()
                val mailPref = binding.mail.text.toString()
                val passwordPref = binding.password.text.toString()

                editor.putString(KEY_MAIL, mailPref)
                editor.putString(KEY_PASSWORD, passwordPref)
                editor.apply()

                println("mail shared prefernces:: $mailPref")
                println("Password s preferences:: $passwordPref")

                // findNavController().navigate(R.id.action_registerFragment_to_introFragment)
            }else{
                Toast.makeText(context, "Failed to register in the system!", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.registerFragment.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.registerFragment.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }

        }

        viewModel.isSendData.observe(viewLifecycleOwner) { isSendData ->
            if (isSendData) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToIntroFragment()
                NavHostFragment.findNavController(this).navigate(action)
                Toast.makeText(context, "Registered Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Registered Failed", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
