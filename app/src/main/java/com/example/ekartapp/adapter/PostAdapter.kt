package com.example.ekartapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ekartapp.R
import com.example.ekartapp.data.ResponseClass

class PostAdapter(val onClick: OnClick): RecyclerView.Adapter<PostViewHolder>() {
    var responseClassList: MutableList<ResponseClass> =ArrayList<ResponseClass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return PostViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val responseClass = responseClassList[position]
        holder.setData(responseClass)    }

    override fun getItemCount(): Int {
   return responseClassList.size
    }
//    fun FILTER(
//        list2:MutableList<ResponseClass>){
//        responseClassList=list2
//        notifyDataSetChanged()
//
//    }
    fun setData(newList:MutableList<ResponseClass>){
        val diffUtil=EkartDiffUtil(responseClassList,newList)
        val diffResult=DiffUtil.calculateDiff(diffUtil)
        responseClassList=newList
        diffResult.dispatchUpdatesTo(this)

    }
}