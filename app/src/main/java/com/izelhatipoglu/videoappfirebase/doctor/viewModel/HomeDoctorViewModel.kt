package com.izelhatipoglu.videoappfirebase.doctor.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel

class HomeDoctorViewModel(application: Application): BaseViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    val isLoading = MutableLiveData<Boolean>()

    fun getData(){

    }
}