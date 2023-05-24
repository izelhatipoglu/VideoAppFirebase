package com.izelhatipoglu.videoappfirebase.doctor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.izelhatipoglu.videoappfirebase.databinding.RecylerDoctorRowBinding
import com.izelhatipoglu.videoappfirebase.model.DoctorSickList

class MyDoctorAdapter (list: ArrayList<DoctorSickList>, private val onItemClicked: IsClickedDoctor): RecyclerView.Adapter<MyDoctorAdapter.HolderDoctor>(){

    private var sickList = list
    class HolderDoctor(val binding: RecylerDoctorRowBinding): RecyclerView.ViewHolder(binding.root)

    interface  IsClickedDoctor{
        fun clickedDoctor(doctorSickList: DoctorSickList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDoctor {
        val binding = RecylerDoctorRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HolderDoctor(binding)
    }

    override fun onBindViewHolder(holder: HolderDoctor, position: Int) {
        holder.binding.sickText.text = sickList[position].sickName
        holder.itemView.setOnClickListener {
            sickList[position].let { it -> onItemClicked.clickedDoctor(it)}
        }

        if(sickList[position].successRate == "3"){
            holder.binding.heart3.visibility = View.VISIBLE
            holder.binding.heart2.visibility = View.VISIBLE
            holder.binding.heart1.visibility = View.VISIBLE
        }else if(sickList[position].successRate == "2"){
            holder.binding.heart2.visibility = View.VISIBLE
            holder.binding.heart1.visibility = View.VISIBLE
        }else{
            holder.binding.heart1.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return sickList.size
    }

    fun updateListDoctor(newSickList: ArrayList<DoctorSickList>){
        sickList.clear()
        sickList.addAll(sickList)
        notifyDataSetChanged()
    }

}