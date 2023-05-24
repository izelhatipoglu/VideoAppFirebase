package com.izelhatipoglu.videoappfirebase.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.izelhatipoglu.videoappfirebase.databinding.RecylerRowBinding
import com.izelhatipoglu.videoappfirebase.model.VideoInfo

class MyAdapter(list: ArrayList<VideoInfo>, val onItemClicked: IsClicked):RecyclerView.Adapter<MyAdapter.HolderVideo>() {

    private var videoList = list
    class HolderVideo(val binding: RecylerRowBinding) : RecyclerView.ViewHolder(binding.root)

    interface IsClicked{
        fun clicked(videoInfo: VideoInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderVideo {
        val binding = RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HolderVideo(binding)
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        holder.binding.videoText.text = videoList[position].videoName
        holder.itemView.setOnClickListener{
            videoList[position].let { it1 -> onItemClicked.clicked(it1) }
        }

        if (videoList[position].isCompleted == true){
            holder.binding.mark.visibility = View.VISIBLE
        }else{
            holder.binding.mark.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
       return videoList.size
    }

    fun updateList(newVideoList: ArrayList<VideoInfo>){
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()
    }


}
