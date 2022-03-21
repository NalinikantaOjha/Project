package com.example.ekartapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.ekartapp.R
import com.example.ekartapp.data.ResponseClass
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.item_layout.view.*

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val intent = intent
        val responseClass=intent.getParcelableExtra<ResponseClass>("response")
        Glide.with(ivImageView).load(responseClass?.image)
            .into(ivImageView)
        responseClass?.let { Log.d("naliniimage", it.image) }
        ivImageView.setImageResource(R.drawable.image)
        tvTitle.text= responseClass?.title
        tvDescription.setText(responseClass?.desc  )
        tvPrice.text  = " ₹ "+ responseClass?.price.toString() + ""
        btnRating.setText(responseClass?.rate.toString())
        tvCount.setText("("+responseClass?.count.toString()+")")


    }
}