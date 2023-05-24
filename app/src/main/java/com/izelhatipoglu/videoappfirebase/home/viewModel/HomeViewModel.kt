package com.izelhatipoglu.videoappfirebase.home.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import com.izelhatipoglu.videoappfirebase.model.InfoUser
import com.izelhatipoglu.videoappfirebase.model.VideoInfo

class HomeViewModel(application: Application): BaseViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    val isLoading = MutableLiveData<Boolean>()

    val videoInfoList = MutableLiveData<ArrayList<VideoInfo>>()
    private val videoClearList = ArrayList<VideoInfo>()
     val completedVideoIdList = MutableLiveData<ArrayList<String>>()

    val userData = MutableLiveData<InfoUser>()

    fun getData(){
        isLoading.postValue(true)
        db.collection("Video Info").get().addOnSuccessListener {
            if(it != null){
                videoClearList.clear()
                for (document in it.documents){
                    val videoUrl = document.get("videoUrl") as? String
                    val videoName = document.get("videoName") as? String
                    val videoId = document.get("videoId") as? String
                    val videoInfo = VideoInfo( videoId,videoUrl,videoName)
                    videoClearList.add(videoInfo)
                }
                videoInfoList.postValue(videoClearList)
            }
            isLoading.postValue(false)
        }.addOnFailureListener {
            Log.d("deneme", "get failed with datayramy ", it)
            isLoading.postValue(false)
        }

    }



    fun getUserData(){
        val docRef = db.collection("userData").document(auth.currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userName = document.get("name") as? String
                    val userId = document.get("id") as? String
                    val userMail = document.get("mail") as? String
                    val completedVideoList = document.get("completedVideoId") as? ArrayList<String>
                    val userVideoInfo = document.get("userVideoInfo") as? ArrayList<Map<String,Any>>

                    userData.postValue(InfoUser(
                        id = userId,
                        name = userName,
                        mail = userMail,
                    //    completedVideoId = completedVideoList,
                        userVideoInfo =userVideoInfo
                    ))

                    getData()

                } else {
                    Log.d("deneme", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("deneme", "get failed with ", exception)
            }
    }

    fun getCurrentUserCompletionVideoId(){
        db.collection("userData").document(auth.currentUser!!.uid).collection("completedVideoId").get().addOnSuccessListener {
            if(it != null){
                val idList = ArrayList<String>()
                for (document in it.documents){
                    println( "document:: ${document.id}  value:: ${document.data}")
                    val idArray = document.get("completedVideoId") as? ArrayList<String>
                    if (idArray?.size != 0){
                        if (idArray != null) {
                            idList.addAll(idArray)
                        }
                    }else{
                        idList.addAll(arrayListOf())
                    }
                }
                completedVideoIdList.postValue(idList)
            }else{
                completedVideoIdList.postValue(arrayListOf())
            }

        }.addOnFailureListener {
            Log.d("deneme", "get failed with datayramy ", it)
        }
    }
}