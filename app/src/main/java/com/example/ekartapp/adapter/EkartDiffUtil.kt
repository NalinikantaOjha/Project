package com.example.ekartapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.ekartapp.data.ResponseClass

class EkartDiffUtil(val oldList:MutableList<ResponseClass>,val newList:MutableList<ResponseClass>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
return newList.size   }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
return oldList[oldItemPosition].id==newList[newItemPosition].id   }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].id==newList[newItemPosition].id->{
                false
            }
                else->true
        }
    }
}