package com.example.ekartapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ekartapp.R
import com.example.ekartapp.adapter.PostViewHolder
import com.example.ekartapp.data.ResponseClass

class PostAdapter(var responseClassList: MutableList<ResponseClass> ): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val responseClass = responseClassList[position]
        holder.setData(responseClass)    }

    override fun getItemCount(): Int {
   return responseClassList.size
    }
    fun updateRecyclerViewList( responseClassList: MutableList<ResponseClass>) {
        this.responseClassList = responseClassList
        notifyDataSetChanged()
    }

}