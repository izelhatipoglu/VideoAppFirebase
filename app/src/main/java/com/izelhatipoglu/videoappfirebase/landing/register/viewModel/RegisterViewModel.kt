package com.izelhatipoglu.videoappfirebase.landing.register.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import com.izelhatipoglu.videoappfirebase.model.DoctorData
import kotlin.collections.ArrayList

class RegisterViewModel(application: Application): BaseViewModel(application) {

    private var auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val isLoading = MutableLiveData<Boolean>()

    val isSendData = MutableLiveData<Boolean>()
    var isRegister = MutableLiveData<Boolean>()

    var doctorIdList = ArrayList<DoctorData>()

    var doctorNameData = MutableLiveData<List<String>>()
    private var doctorNameList = ArrayList<String>()

    var doctorId :String? = null
    var sickId :String? = null


    fun register(mail: String, password: String, name: String,doctorName: String){

        if(mail.isNotEmpty() && password.isNotEmpty()){
            isLoading.postValue(true)
            auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener {
                if(it.isSuccessful){
                    sendData(name,mail)
                    sendDoctorName(doctorName,name,doctorIdList,auth.currentUser!!.uid)
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
        sickId = auth.currentUser!!.uid
        val postMap = hashMapOf<String, Any>()
        postMap.put("name",name)
        postMap.put("mail",mail)
        postMap.put("id", sickId!!)

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

    fun getDoctorNameData(){
        doctorNameList.add("Choose Your Doctor")
        db.collection("doctorData").get().addOnSuccessListener {
            if(it != null){
                for(document in it.documents){
                    val doctorName = document.get("DoctorName") as? String
                    val doctorId =document.get("DoctorId") as? String
                    if (doctorName != null) {
                        if (doctorId != null) {
                            doctorIdList.add(DoctorData(doctorId,doctorName))
                            doctorNameList.add(doctorName)
                        }
                    }
                }
                doctorNameData.value = doctorNameList

            }

        }.addOnFailureListener {
            Log.d("deneme", "get failed with datayramy ", it)
        }

    }

    private fun sendDoctorName(doctorName: String, name: String, list: List<DoctorData>,sickId: String ){
        val postMap = hashMapOf<String,Any>()
        postMap.put("Sick name",name)
        postMap.put("Sick id",sickId)

        for (i in list.indices){
            if(list[i].nameDoctor == doctorName){
                doctorId = list[i].idDoctor
                println("i : $i")
            }
        }

        doctorId?.let { doctorId ->
                db.collection("doctorData").document(doctorId).collection("SickList").document().set(postMap)
                    .addOnSuccessListener {
                        println("success")
                    }.addOnFailureListener {
                        println("failure")
                    }

        }

    }
}

