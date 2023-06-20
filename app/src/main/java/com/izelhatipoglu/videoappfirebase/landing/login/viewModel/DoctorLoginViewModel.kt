package com.izelhatipoglu.videoappfirebase.landing.login.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
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
    val doctorMailListL = ArrayList<String>()
    val controldata = MutableLiveData<Boolean>()


    fun login(mail: String, password: String){
            if (mail.isNotEmpty() && password.isNotEmpty()) {
                println("login $password")
                isLoding.postValue(true)
                auth.signInWithEmailAndPassword(mail,password)
                    .addOnCompleteListener { p0 ->
                        isLoding.postValue(false)
                        if (p0.isSuccessful) {
                            println(auth.currentUser?.email)
                            loginData.postValue(true)
                        } else {
                            loginData.postValue(false)
                            println("hatalı giriş")
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
                doctorMailListL.addAll(doctorClearList)
                doctorMailList.postValue(doctorClearList)
                println(doctorClearList.size)
                println(doctorClearList[0])
                control(mailUser,doctorMailListL)

           }
            isLoding.postValue(false)
        }.addOnFailureListener {
            Log.d("deneme", "get failed with datayramy ", it)
            isLoding.postValue(false)
        }

    }


    fun control(mail: String, list: List<String>){
        var loginRequest = false
        for (doctors in list) {
            if (doctors == mail) {
                println("loginRequest :b $loginRequest")
                loginRequest = true
            }
        }
        controldata.value = loginRequest
        println("mail : $mail")
      /*  if(list.contains(mail)){
            println(" If içinde ")
            controldata.postValue(true)
        }else{
            println("if disinda")
            controldata.postValue(false)
        } */
    }

}