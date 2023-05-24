package com.izelhatipoglu.videoappfirebase.landing.login.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel

class DoctorLoginViewModel(application: Application): BaseViewModel(application){


    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val loginData = MutableLiveData<Boolean>()
    val isLoding = MutableLiveData<Boolean>()

    val doctorMailList = MutableLiveData<ArrayList<String>>()
    val doctorClearList = ArrayList<String>()
    val controldata = MutableLiveData<Boolean>()

    var controlInfo = false

    fun login(mail: String, password: String){
            if (mail.isNotEmpty() && password.isNotEmpty()) {
                isLoding.postValue(true)
                auth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener { p0 ->
                        isLoding.postValue(false)
                        if (p0.isSuccessful) {
                            println(auth.currentUser?.email)
                            loginData.postValue(true)
                        } else {
                            loginData.postValue(false)
                        }
                    }
                    .addOnFailureListener {
                        isLoding.postValue(false)
                        println("hata $it")
                    }
            }


    }

    fun getData(mailUser: String){
        isLoding.postValue(true)
        db.collection("doctorData").get().addOnSuccessListener {
            if(it != null){
                doctorClearList.clear()
                for (document in it.documents){
                    val mail = document.get("mail") as? String
                    if (mail != null) {
                        doctorClearList.add(mail)
                    }
                }
                doctorMailList.postValue(doctorClearList)
                control(mailUser,doctorClearList)

           }
            isLoding.postValue(false)
        }.addOnFailureListener {
            Log.d("deneme", "get failed with datayramy ", it)
            isLoding.postValue(false)
        }

    }


    fun control(mail: String,list: List<String>){
        println("Control geliyor ${list.size} $mail")
        println("Liste : ${list[0]}")
        if(list.contains(mail)){
            println(" If içinde ")
            controldata.postValue(true)
        }else{
            controldata.postValue(false)
        }
    }

}