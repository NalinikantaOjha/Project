package com.example.ekartapp.data

import android.net.UrlQuerySanitizer
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URL
@Parcelize
data class ResponseClass(val id:Int, val title:String,val image:String,val price:Double,val desc:String,val rate:Double,val count:Int):Parcelable
{

}