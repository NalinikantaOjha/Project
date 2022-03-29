package com.example.ekartapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ResponseClass(
    @PrimaryKey(autoGenerate = false)val id:Int,
    val title:String, val image:String, val price:Double, val desc:String, val rate:Double, val count:Int, val catagory:String):Parcelable
