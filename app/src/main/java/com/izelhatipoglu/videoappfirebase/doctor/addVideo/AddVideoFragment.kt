package com.izelhatipoglu.videoappfirebase.doctor.addVideo


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.izelhatipoglu.videoappfirebase.R
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentAddVideoBinding
import com.izelhatipoglu.videoappfirebase.doctor.addVideo.viewModel.AddVideoViewModel
import kotlinx.android.synthetic.main.fragment_add_video.*
import kotlinx.android.synthetic.main.fragment_video_player.*
import java.sql.Array
import kotlin.collections.ArrayList as ArrayList1


class AddVideoFragment : BaseFragment<AddVideoViewModel, FragmentAddVideoBinding>() {

    private val VIDEO_PICK_GALLERY_CODE = 100
    private val CAMERA_REQUEST_CODE = 102

    private var videoUri: Uri? = null
    private var title: String? = null
    var selectedVideo: Uri? = null
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>


    override fun getViewModel(): Class<AddVideoViewModel> = AddVideoViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAddVideoBinding = FragmentAddVideoBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var cameraPermissions = arrayOf(android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        ActivityCompat.requestPermissions(
            requireActivity(), cameraPermissions,
            CAMERA_REQUEST_CODE
        )

        initUI()
        registerLauncher()

    }

    private fun initUI() {
        binding.button.setOnClickListener {
            title = binding.titleEt.text.toString()
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(requireContext(), "Title is required", Toast.LENGTH_LONG).show()
            } else if (videoUri == null) { //video seçilmediyse eğer
                Toast.makeText(requireContext(), "Pick the video", Toast.LENGTH_LONG).show()
            } else {
                viewModel.uploadVideo(videoUri, title!!)
            }
        }

        binding.videoView.setOnClickListener {
            checkPermission()
        }
    }

    /*
        private fun checkPermission(context:Context): Boolean {
            val result = ContextCompat.checkSelfPermission(
                context,android.Manifest.permission.CAMERA
            ) ==PackageManager.PERMISSION_GRANTED

            val result2 = ContextCompat.checkSelfPermission(
                context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )== PackageManager.PERMISSION_GRANTED

            return result && result2
        }


     */
    private fun videoPickGallery() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
            Intent.createChooser(intent, "Choose video"),
            VIDEO_PICK_GALLERY_CODE
        )
    }

    /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

           if(resultCode == RESULT_OK) {
               if (requestCode == VIDEO_PICK_GALLERY_CODE) {
                   println("data :: ${data?.data.toString()}")
                   videoUri = data?.data
                 //  setVideoViewtoView()
               }
           }else{
               Toast.makeText(requireContext(),"Cancelled",Toast.LENGTH_SHORT).show()
           }
           super.onActivityResult(requestCode, resultCode, data)
       }
   */
       private fun setVideoViewtoView(videoUri: Uri){
          videoView.setVideoURI(videoUri)
           videoView.requestFocus()
           videoView.setOnPreparedListener{
               videoView.pause()
           }
       }



    private fun registerLauncher() {

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.clipData != null){
                        println("bos degil")
                        val intentFromResult = result.data!!.clipData?.getItemAt(0)?.uri
                        if (intentFromResult != null) {

                            println("burada")
                            selectedVideo = intentFromResult

                            selectedVideo?.let {
                                //  videoUri = it
                                println("data:: $intentFromResult")
                                setVideoViewtoView(it)

                            }
                        }
                    }

                }

            }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intentToGalley =
                        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGalley)
                } else {
                    Toast.makeText(context, "Permisson needed!", Toast.LENGTH_LONG).show()
                }

            }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {
                Snackbar.make(requireView(),
                    "Permission needed for gallery",
                    Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {

                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()

            } else {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGalley =
                Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGalley)


        }
    }

}
