package com.izelhatipoglu.videoappfirebase.landing.register.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import java.util.*
import kotlin.math.log

class RegisterViewModel(application: Application): BaseViewModel(application) {

    private var auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val isLoading = MutableLiveData<Boolean>()
    val loginData = MutableLiveData<Boolean>()

    val isSendData = MutableLiveData<Boolean>()
    var isRegister = MutableLiveData<Boolean>()

    fun register(mail: String, password: String, name: String, id: String){

        if(mail.isNotEmpty() && password.isNotEmpty()){
            isLoading.postValue(true)
            auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener {
                if(it.isSuccessful){
                    sendData(name,mail,id)
                    isLoading.postValue(true)
                    isRegister.postValue(true)
                    loginData.postValue(true)
                }else{
                    isLoading.postValue(false)
                    loginData.postValue(false)
                    println(it.exception?.localizedMessage)
                }
            }
        }
    }

    fun sendData(name: String,mail: String,id: String){
        val postMap = hashMapOf<String, Any>()
        postMap.put("name",name)
        postMap.put("mail",mail)
        postMap.put("id",id)

        isLoading.postValue(true)
        db.collection("userData").add(postMap).addOnSuccessListener {
            isSendData.postValue(true)
        }.addOnFailureListener {
            isLoading.postValue(false)
        }
    }
}