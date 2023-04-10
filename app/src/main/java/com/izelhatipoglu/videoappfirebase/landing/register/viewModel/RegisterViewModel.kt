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

    val isSendData = MutableLiveData<Boolean>()
    var isRegister = MutableLiveData<Boolean>()

    fun register(mail: String, password: String, name: String){

        if(mail.isNotEmpty() && password.isNotEmpty()){
            isLoading.postValue(true)
            auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener {
                if(it.isSuccessful){
                    sendData(name,mail)
                    isRegister.postValue(true)
                }else{
                    isRegister.postValue(false)
                    println(it.exception?.localizedMessage)
                }
            }
            isLoading.postValue(false)
        }
    }

    fun sendData(name: String,mail: String){
        val postMap = hashMapOf<String, Any>()
        postMap.put("name",name)
        postMap.put("mail",mail)
        postMap.put("id",auth.currentUser!!.uid)

        val completedVideoIdArray = hashMapOf<String,Any>()
        postMap.put("completedVideoId", arrayListOf<String>())

        isLoading.postValue(true)
        db.collection("userData").document(auth.currentUser!!.uid).set(postMap).addOnSuccessListener {
            db.collection("userData").document(auth.currentUser!!.uid).collection("completedVideoId").document("idInfo").set(completedVideoIdArray)
                .addOnSuccessListener {
                    println("success")
                }.addOnFailureListener {
                    println("failure")
                }
            isSendData.postValue(true)
        }.addOnFailureListener {
            isLoading.postValue(false)
        }


        isLoading.postValue(false)
    }
}