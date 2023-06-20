package com.izelhatipoglu.videoappfirebase.doctor.home.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import com.izelhatipoglu.videoappfirebase.model.DoctorSickList

class HomeDoctorViewModel(application: Application): BaseViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    val isLoading = MutableLiveData<Boolean>()
    val doctorName = MutableLiveData<String>()

    val sickList = MutableLiveData<ArrayList<DoctorSickList>>()
    private val clearSickList = ArrayList<DoctorSickList>()


    fun getData(){
        isLoading.postValue(true)
        println(auth.currentUser!!.uid)
        db.collection("doctorData").document(auth.currentUser!!.uid).collection("SickList").get().addOnSuccessListener {
            if(it != null){
                clearSickList.clear()
                println(it.documents.size)
                for (document in it.documents){
                    val sickId = document.get("Sick id") as? String
                    println("sikkkk id :$sickId")
                    val sickName = document.get("Sick name") as? String
                    val succes = document.get("successRate") as? String
                    val sickInfo = DoctorSickList(sickId,sickName,succes)
                    clearSickList.add(sickInfo)
                }
                println(clearSickList.toString())
                sickList.value =clearSickList

            }
            isLoading.postValue(false)
        }.addOnFailureListener {
            Log.d("deneme", "get failed with datayramy ", it)
            isLoading.postValue(false)
        }
    }

    fun getDoctorName(){
        db.collection("doctorData").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            if(it !=null){
                var doctorNameData = it.get("DoctorName") as? String
                doctorName.value = doctorNameData!!
            }

        }
    }
}