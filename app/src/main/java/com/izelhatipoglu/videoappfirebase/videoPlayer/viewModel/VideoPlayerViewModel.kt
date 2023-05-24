package com.izelhatipoglu.videoappfirebase.videoPlayer.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import com.izelhatipoglu.videoappfirebase.model.UserVideoInfo

class VideoPlayerViewModel(application: Application): BaseViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val currentvideoInfo = MutableLiveData<UserVideoInfo>()
    val isPlay = MutableLiveData<Boolean>()

    val liveIdList = MutableLiveData<ArrayList<String>?>()

    val isCompleted = MutableLiveData<Boolean>()

    fun getUserData(videoId: String){

       val docRef =db.collection("userData").document(auth.currentUser!!.uid).collection("videoInfo").whereEqualTo("videoId",videoId)
        docRef.get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty){
                    println("result not null")
                    for (document in result) {
                        val isCompleted = document.data["isCompleted"] as? Boolean
                        val numberOfCompletion = document.data["numberOfCompletion"] as? String
                        val numberOfPause = document.data["numberOfPause"] as? String
                        val videoPosition = document.data["videoPosition"] as? String

                        val video = UserVideoInfo(isCompleted, numberOfCompletion, videoId, numberOfPause, videoPosition,true)
                        println("exists video info :: $videoPosition  ${video.isExists}")


                        currentvideoInfo.postValue(video)
                       // isLoading.postValue(false)
                        break

                    }
                }else{
                    val video = UserVideoInfo(false,"0",videoId,"0","0",isExists = false)
                    currentvideoInfo.postValue(video)
                }


            }
            .addOnFailureListener {
                println("getUserDataVideoPlayerError ::: ${it.localizedMessage}")

            }


    }

    fun compareCompletionVideoId(userCompletionVideoId: String){
        val docRef =db.collection("userData").document(auth.currentUser!!.uid).collection("completedVideoId").whereArrayContains("completedVideoId",userCompletionVideoId)
        docRef.get()
            .addOnSuccessListener { result ->
                    if (result.size() == 0){
                        updateCompletionVideoId(liveIdList.value ?: arrayListOf(),userCompletionVideoId)
                    }

            }

    }

    private fun updateCompletionVideoId(userCompletionVideoIdList: ArrayList<String>,  newId: String){
        println(" First updateCompletşonVideoId : ${userCompletionVideoIdList.size}")
        userCompletionVideoIdList.add(newId)

        println(" updateCompletşonVideoId : ${userCompletionVideoIdList.size}")
        val map = hashMapOf(
            "completedVideoId" to userCompletionVideoIdList
        )

        val docRef =db.collection("userData").document(auth.currentUser!!.uid).collection("completedVideoId").document("idInfo").set(map)
        docRef.addOnSuccessListener {
            println("updated list")

        }
            .addOnFailureListener {
                println("updated failure ")

            }
    }

     fun getAllCompletionList(){

        val docRef =db.collection("userData").document(auth.currentUser!!.uid).collection("completedVideoId").document("idInfo")
        docRef.get().addOnSuccessListener {

            val completedVideoList = it.get("completedVideoId") as? ArrayList<String>


            if (completedVideoList != null) {
                liveIdList.value = completedVideoList


            }


        }
    }

    fun sendPauseData(userVideoInfo: UserVideoInfo){

        val videoInfo = hashMapOf(
            "isCompleted" to userVideoInfo.isCompleted,
            "numberOfCompletion" to userVideoInfo.numberOfCompletion,
            "numberOfPause" to userVideoInfo.numberOfPause,
            "videoPosition" to userVideoInfo.videoPosition,
            "videoId" to userVideoInfo.videoId
        )
        db.collection("userData").document(auth.currentUser!!.uid).collection("videoInfo").document(userVideoInfo.videoId!!).set(videoInfo as Map<String, Any>)
            .addOnSuccessListener {
                println("güncellendi")

            }
            .addOnFailureListener {
                println("güncelleme hatası :: ${it.localizedMessage}")

            }
    }
}