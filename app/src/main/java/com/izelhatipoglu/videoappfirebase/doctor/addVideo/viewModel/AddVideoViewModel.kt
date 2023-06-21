package com.izelhatipoglu.videoappfirebase.doctor.addVideo.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.izelhatipoglu.videoappfirebase.base.BaseViewModel
import java.util.*
import kotlin.collections.HashMap

class AddVideoViewModel(application: Application) : BaseViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    val controlVideoUpload = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

   /* fun uploadVideo(title : String,videoUri: Uri){

        val uuid = UUID.randomUUID()
        val videoName = "$title.mp4"

        val storage = Firebase.storage
        val reference = storage.reference
        val videoReference= reference.child("video").child(videoName)

        val filePathName = "Videos/video_$title"
        val timeStap = ""+System.currentTimeMillis()
        val storageReference = storage.getReferenceFromUrl(filePathName)

        storageReference.putFile(videoUri).addOnSuccessListener { taskSnapshot->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isSuccessful);
            val downloadUri = uriTask.result
            if(uriTask.isSuccessful){
                val hashMap = HashMap<String, Any>()
                hashMap["id"] = "$title+$timeStap"
                hashMap["title"] = "$title"
               // hashMap["downloadUri"]
                val dbReference = FirebaseDatabase.getInstance().getReference("Video Info")
                dbReference.child(timeStap)
                    .setValue(hashMap).addOnSuccessListener { taskSnapshot->
                        controlVideoUpload.value = true

                    }.addOnFailureListener {
                        controlVideoUpload.value = false
                    }

            }
        }.addOnFailureListener{
            controlVideoUpload.value = false
        }
    }

    */

    fun uploadVideo(videoUri: Uri?, videoTitle:String){

        //UUID -> image name
        val uuid = UUID.randomUUID()

        val storage = Firebase.storage
        val reference = storage.reference
        val videosReference = reference.child("videos").child(uuid.toString())

        if (videoUri != null) {
            isLoading.postValue(false)
            videosReference.putFile(videoUri).addOnSuccessListener {

                val uploadedPictureReference = storage.reference.child("Videos").child(uuid.toString())
                uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    println(downloadUrl)

                    val postMap = hashMapOf<String,Any>()
                    postMap.put("videoUrl",downloadUrl)
                    postMap.put("videoName",videoTitle)
                    postMap.put("videoId",uuid)

                    isLoading.postValue(true)
                    db.collection("Video Info").document(uuid.toString()).update(postMap).addOnSuccessListener {
                        isLoading.postValue(true)

                    }.addOnFailureListener {
                        println("hata ${it.localizedMessage}") }
                    isLoading.postValue(false)

                }
            }
        }
        else{
            isLoading.postValue(true)
        }
    }

    fun uploadVideo(selectedVideo: Uri?){

        val uuid = UUID.randomUUID()

    }
}