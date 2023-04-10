package com.izelhatipoglu.videoappfirebase.home.viewModel

import android.R.attr
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import com.izelhatipoglu.videoappfirebase.model.InfoUser


class SettingViewModel(application: Application) : BaseViewModel(application) {

    var updateData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val updatePassword = MutableLiveData<Boolean>()
    val updateMail = MutableLiveData<Boolean>()

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance().currentUser

    val userInfo = MutableLiveData<InfoUser>()

    var currentDocument = MutableLiveData<String>()


    fun updateData(name: String, mail: String, password: String,oldPassword: String) {

        auth?.let {
            val credential = EmailAuthProvider.getCredential(it.email!!,oldPassword)
            auth.reauthenticate(credential).addOnCompleteListener {
                auth.updatePassword(password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.addOnSuccessListener {
                            println(" PASSWORD değiştirildi")
                            auth.updateEmail(mail).addOnCompleteListener {
                                println("password change")
                                if (it.isSuccessful) {
                                    it.addOnSuccessListener {
                                        println(" mail değiştirildi")
                                        updatePassword.postValue(true)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }



        val postMap = hashMapOf<String,Any>()
        postMap.put("name",name)
        postMap.put("mail",mail)

        isLoading.postValue(true)
        db.collection("userData").document(auth!!.uid).update(postMap).addOnSuccessListener {
            updateData.postValue(true)
        }.addOnFailureListener {
            println("hata ${it.localizedMessage}")
        }
        isLoading.postValue(false)
    }

    fun getData() {

        val docRef = db.collection("userData").document(auth!!.uid)
        docRef.get()
            .addOnSuccessListener { result->
                if(result.data!!.isNotEmpty()){
                    println("result not null")
                    val mail = result.get("mail") as? String
                    val name = result.get("name") as? String

                    val userInfoModel = InfoUser(mail = mail, name = name)
                    userInfo.postValue(userInfoModel)
                }
            }.addOnFailureListener {
                Log.d("deneme", "get failed with datayramy ", it)
                isLoading.postValue(false)
            }
    }


}

