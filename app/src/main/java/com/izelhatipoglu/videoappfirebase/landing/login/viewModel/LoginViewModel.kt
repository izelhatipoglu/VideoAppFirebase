package com.izelhatipoglu.videoappfirebase.landing.login.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel

class LoginViewModel(application: Application): BaseViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val loginData = MutableLiveData<Boolean>()
    val isLoding = MutableLiveData<Boolean>()


    fun login(mail: String, password: String){
        if(mail.isNotEmpty() && password.isNotEmpty()){
            isLoding.postValue(true)
            auth.signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {
                        isLoding.postValue(false)
                        if(p0.isSuccessful){
                            println(auth.currentUser?.email)
                            loginData.postValue(true)
                        }else{
                            loginData.postValue(false)
                        }
                    }

                }
                )
                .addOnFailureListener {
                    isLoding.postValue(false)
                    println("hata $it")
                }
        }
    }


}