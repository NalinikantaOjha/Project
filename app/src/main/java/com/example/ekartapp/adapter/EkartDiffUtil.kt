package com.example.ekartapp.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.ekartapp.data.ResponseClass

class EkartDiffUtil(private val oldList:MutableList<ResponseClass>, private val newList:MutableList<ResponseClass>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
return newList.size   }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d("check","areItemsTheSame call")
return oldList[oldItemPosition].id==newList[newItemPosition].id
  }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d("check","areContentsTheSame ")

        return oldList[oldItemPosition].id==newList[newItemPosition].id

    }
}