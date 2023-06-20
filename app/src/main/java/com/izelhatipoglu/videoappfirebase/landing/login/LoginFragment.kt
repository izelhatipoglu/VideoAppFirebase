package com.izelhatipoglu.videoappfirebase.landing.login

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
import com.izelhatipoglu.videoappfirebase.databinding.FragmentLoginBinding
import com.izelhatipoglu.videoappfirebase.home.HomeActivity
import com.izelhatipoglu.videoappfirebase.landing.login.viewModel.LoginViewModel

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private val auth = FirebaseAuth.getInstance()
   lateinit var sharedPreference: SharedPreferences
   lateinit var editor: SharedPreferences.Editor

    val LOGIN_TYPE = "login_type"


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
       /* sharedPreference = requireActivity().getSharedPreferences("com.izelhatipoglu.videoappfirebase",Context.MODE_PRIVATE)
        editor = sharedPreference.edit()


        val currentUser = auth.currentUser
        if (currentUser != null){

            //daha önce giriş yaptı direkt home gidiyor
            val mailPreferences = sharedPreference.getString("mail","")
            val passwordPreferences = sharedPreference.getString("password","")
            val typePreferences = sharedPreference.getString("login_type","")
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

*/

    }

    private fun handleClick() {
        binding.button.setOnClickListener {
            val mail = binding.mail.text.toString()
            val password = binding.password.text.toString()
            viewModel.login(mail, password)
        }

        binding.signUpBtn.setOnClickListener{
            println(" LOGIN : Butona basıldı")
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.loginDoctor.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToDoctorLoginFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun observeData() {
        viewModel.loginData.observe(viewLifecycleOwner) { loginData ->
            if (loginData) {
                editor.putString(LOGIN_TYPE,"sick")
                editor.apply()
                startActivity(Intent(requireActivity(), HomeActivity::class.java))
                activity?.finish()
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
