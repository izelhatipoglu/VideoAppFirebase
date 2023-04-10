package com.izelhatipoglu.videoappfirebase.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.izelhatipoglu.videoappfirebase.base.BaseFragment
import com.izelhatipoglu.videoappfirebase.databinding.FragmentHomeBinding
import com.izelhatipoglu.videoappfirebase.home.adapter.MyAdapter
import com.izelhatipoglu.videoappfirebase.home.viewModel.HomeViewModel
import com.izelhatipoglu.videoappfirebase.model.VideoInfo
import com.izelhatipoglu.videoappfirebase.videoPlayer.VideoPlayerActivity

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(){

    private lateinit var myAdapter: MyAdapter


    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        observeData()
        initUI()

       // activity?.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        myAdapter = MyAdapter(arrayListOf(), object: MyAdapter.IsClicked{
            override fun clicked(videoInfo: VideoInfo) {

                val b= Bundle()
                b.putSerializable("videoInfo",videoInfo)
                val intent = Intent(requireActivity(), VideoPlayerActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                activity?.finish()
               // val action = HomeFragmentDirections.actionHomeFragmentToVideoPlayerFragment(videoInfo)
               // NavHostFragment.findNavController(this@HomeFragment).navigate(action)
            }
        })
        binding.recylerView.layoutManager = LinearLayoutManager(context)
        binding.recylerView.adapter= myAdapter


        viewModel.getCurrentUserCompletionVideoId()


    }

    private fun observeData(){
        viewModel.userData.observe(viewLifecycleOwner){ userInfo ->
            userInfo?.let {
                binding.tvName.text = it.name
            }

        }
        viewModel.videoInfoList.observe(viewLifecycleOwner){ videoInfoList ->
            if(videoInfoList.isNotEmpty() && viewModel.completedVideoIdList.value?.size != 0){
                for(video in videoInfoList){
                    for (videoId in viewModel.completedVideoIdList.value!!){
                        if (video.videoId.equals(videoId)){
                            video.isCompleted = true
                        }
                    }
                }
                myAdapter.updateList(videoInfoList)
            }else{
                myAdapter.updateList(videoInfoList)
            }
        }


    }

    private fun initUI(){

        binding.btUser.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
            NavHostFragment.findNavController(this@HomeFragment).navigate(action)
        }
    }
/*
    private fun setVideoUrl(videoInfo: VideoInfo, holder: MyAdapter.HolderVideo){

        val videoUrl:String? = videoInfo.videoUrl
        val mediaController = MediaController(context)
        mediaController.setAnchorView(holder.itemView)
        val videoUri = Uri.parse(videoUrl)

        holder.itemView.setMediaController(mediaController)
        holder.itemView.setVideoURI(videoUri)
        holder.itemView.requestFocus()

        holder.itemView.setOnPre


    }
*/
}