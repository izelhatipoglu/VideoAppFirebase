package com.izelhatipoglu.videoappfirebase.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.izelhatipoglu.videoappfirebase.util.DataChangeListener
import com.izelhatipoglu.videoappfirebase.util.GlobalData
import java.lang.Exception

abstract class BaseFragment<VM: ViewModel, B: ViewBinding> : Fragment() {

    lateinit var dataStateChangeListener: DataChangeListener

    private var loadingDialog: Dialog? =null

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =getFragmentBinding(inflater,container)
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(getViewModel())
        return binding.root
    }
    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    fun hideKeyBoard(){
        try{
            if(activity?.currentFocus !=null){
                val inputMethodManager = context?.getSystemService(
                    Context.INPUT_METHOD_SERVICE
                )as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus!!.windowToken,0)

            }
        }catch (e: Exception){
            Log.e("hideKeyboardError","error: ${e.printStackTrace()}")
        }
    }

    fun displayProgressBar(display: Boolean){
        try{
            if(display){
                showLoading()
            }else{
                hideLoading()
            }
        }catch (e: Exception){
            Log.e("hideKeyboardError","error: ${e.printStackTrace()}")
        }
    }

    private fun showLoading(){
        try {
            hideLoading()
            loadingDialog = context?.let { GlobalData.showLoadingDialog(it) }
        }catch (e: Exception){
            Log.e("hideKeyboardError","error: ${e.printStackTrace()}")
        }
    }

    private fun hideLoading(){
        try {
            loadingDialog?.let { if(it.isShowing)it.cancel() }
        }catch (e:java.lang.Exception){
            Log.e("hideKeyboardError","error: ${e.printStackTrace()}")
        }
    }

    fun checkConnectivity(){
        try{
            if(!GlobalData.checkForInternet(requireContext())){

            }
        }catch (e: Exception){
            Log.e("hideKeyboardError","error: ${e.printStackTrace()}")
        }
    }
}