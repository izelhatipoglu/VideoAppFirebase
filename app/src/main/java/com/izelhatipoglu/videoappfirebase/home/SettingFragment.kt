package com.izelhatipoglu.videoappfirebase.home

import android.accounts.AccountManager.KEY_PASSWORD
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentSettingBinding
import com.izelhatipoglu.videoappfirebase.home.viewModel.SettingViewModel
import com.izelhatipoglu.videoappfirebase.landing.LandingActivity

class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var  editor : SharedPreferences.Editor
    private var oldPassword = ""

    override fun getViewModel(): Class<SettingViewModel> = SettingViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSettingBinding = FragmentSettingBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreference = requireActivity().getSharedPreferences("com.izelhatipoglu.videoappfirebase",
            Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        oldPassword = sharedPreference.getString("password","") ?: ""
        println("password: $oldPassword")

        isEnabledEditText(false)
        button()
        observeData()
        handleClick()
        viewModel.getData()
        logOutButton()
    }

    fun logOutButton(){
        binding.button.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireActivity(), LandingActivity::class.java))
            activity?.finish()
        }
    }

    fun button(){
        binding.change.setOnClickListener {
            println("button içine girdi ")
            isEnabledEditText(true)
        }

    }

    private fun observeData(){
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if(isLoading){
                binding.password.isEnabled = false
                binding.password.visibility = View.VISIBLE

            }else{
                binding.password.isEnabled = true
                binding.password.visibility = View.GONE
            }

        }
        viewModel.userInfo.observe(viewLifecycleOwner){user ->
           user.let {
               binding.mail.setText(user.mail.toString())
               binding.name.setText(user.name.toString())
           }

        }

        viewModel.updatePassword.observe(viewLifecycleOwner){ password ->
            if (password){
                val passwordPref = binding.password.text.toString()
                editor.putString(KEY_PASSWORD, passwordPref)
                editor.apply()

                auth.signOut()
                val intent = Intent(activity,LandingActivity::class.java)
                startActivity(intent)
                activity?.finish()
                Toast.makeText(context,"You changed password. Please relogin.",Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun handleClick(){
        binding.buttonSave.setOnClickListener {
            if(binding.password.isEnabled){
                if( binding.mail.text.toString().length > 1 && binding.mail.text.toString().contains("@") && binding.password.text.toString().length > 6 && binding.name.text.toString().length >1){
                    viewModel.updateData(
                        binding.name.editableText.toString(),
                        binding.mail.editableText.toString(),
                        binding.password.editableText.toString(),
                        oldPassword
                    )
                    Toast.makeText(context,"You Have Made Changes", Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(context,"You Haven't Made Any Changes", Toast.LENGTH_SHORT).show()
                }
                }else{
                Toast.makeText(context,"You Haven't Made Any Changes. You entered incorrect information", Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun isEnabledEditText(isEnabled : Boolean){
        binding.mail.isEnabled = isEnabled
        binding.name.isEnabled = isEnabled
        binding.password.isEnabled = isEnabled
    }


}

