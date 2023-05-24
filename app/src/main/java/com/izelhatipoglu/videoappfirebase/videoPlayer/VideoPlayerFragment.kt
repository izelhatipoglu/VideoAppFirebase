package com.izelhatipoglu.videoappfirebase.videoPlayer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.navigation.fragment.NavHostFragment
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentVideoPlayerBinding
import com.izelhatipoglu.videoappfirebase.home.HomeActivity
import com.izelhatipoglu.videoappfirebase.landing.LandingActivity
import com.izelhatipoglu.videoappfirebase.videoPlayer.viewModel.VideoPlayerViewModel
import com.izelhatipoglu.videoappfirebase.model.UserVideoInfo
import com.izelhatipoglu.videoappfirebase.model.VideoInfo

class VideoPlayerFragment : BaseFragment<VideoPlayerViewModel, FragmentVideoPlayerBinding>() {

    private lateinit var videoInfo: VideoInfo
    private var currentVideo = UserVideoInfo()
    private val mediaController by lazy { MediaController(requireContext()) }

    override fun getViewModel(): Class<VideoPlayerViewModel> = VideoPlayerViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentVideoPlayerBinding = FragmentVideoPlayerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoInfo = (activity?.intent?.getSerializableExtra("videoInfo") as? VideoInfo)!!

        initUI()

    }

    private fun initUI() {
        binding.title.text = videoInfo.videoName
        viewModel.getUserData(videoInfo.videoId!!)
        showVideo()
        observeData()

        viewModel.getAllCompletionList()
        binding.ivBack.setOnClickListener {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            activity?.finish()
        }

    }

    private fun observeData() {
        viewModel.currentvideoInfo.observe(viewLifecycleOwner){ currentVideoInfo ->
            if (currentVideoInfo.isExists == true){
                binding.videoView.seekTo(Integer.parseInt(currentVideoInfo.videoPosition))
                binding.videoView.start()
            }else{
                binding.videoView.start()
            }
            currentVideo = currentVideoInfo
        }

        viewModel.isCompleted.observe(viewLifecycleOwner) { isCompleted ->
            if(isCompleted){
                startActivity(Intent(requireActivity(), HomeActivity::class.java))
                activity?.finish()
            }
        }
    }


    private fun showVideo() {

        binding.videoView.setVideoURI(videoInfo.videoUrl?.toUri())
        binding.videoView.requestFocus()
        binding.videoView.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.pause()
                binding.playButton.visibility = View.VISIBLE

            }else{
                binding.videoView.start()
                binding.playButton.visibility = View.GONE
            }
        }
        binding.videoView.setOnCompletionListener {
            currentVideo.numberOfCompletion =(Integer.parseInt(currentVideo.numberOfCompletion!!)+1).toString()
            currentVideo.isCompleted = true
            currentVideo.videoPosition = "0"

            viewModel.sendPauseData(currentVideo)
            viewModel.compareCompletionVideoId(currentVideo.videoId!!)



        }
    }


    override fun onPause() {
        super.onPause()
        binding.videoView.pause()

        if (binding.videoView.currentPosition != binding.videoView.duration){
            currentVideo.numberOfPause = (Integer.parseInt(currentVideo.numberOfPause!!) +1).toString()
            val infoPause = binding.videoView.currentPosition
            currentVideo.videoPosition = infoPause.toString()
            viewModel.sendPauseData(currentVideo)
        }
    }

}