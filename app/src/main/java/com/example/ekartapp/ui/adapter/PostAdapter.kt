package com.example.ekartapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ekartapp.R
import com.example.ekartapp.ui.adapter.iterface.OnClick
import com.example.ekartapp.ui.adapter.iterface.OnEdit
import com.example.ekartapp.data.model.ResponseClass

class PostAdapter(private val onClick: OnClick, private val onEdit: OnEdit): RecyclerView.Adapter<PostViewHolder>() {
    private var responseClassList: MutableList<ResponseClass> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return PostViewHolder(view,onClick,onEdit)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val responseClass = responseClassList[position]
        holder.setData(responseClass)    }

    override fun getItemCount(): Int {
    return responseClassList.size
    }

    fun setData(newList:MutableList<ResponseClass>){
        val diffUtil=EkartDiffUtil(responseClassList,newList)
        val diffResult=DiffUtil.calculateDiff(diffUtil)
        responseClassList=newList
        diffResult.dispatchUpdatesTo(this)

    }
}