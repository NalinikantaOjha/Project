package com.example.ekartapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.ekartapp.R
import com.example.ekartapp.ui.base.BaseActivity
import com.example.ekartapp.data.model.ResponseClass
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : BaseActivity() {
    override fun provideLayoutId(): Int =R.layout.activity_view
    override fun setupView(savedInstanceState: Bundle?) {
        val intent = intent
        val responseClass=intent.getParcelableExtra<ResponseClass>("response")
        if (responseClass!!.id<=20){
            ivImageView.layoutParams.width=ViewGroup.LayoutParams.MATCH_PARENT
            ivImageView.layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT
        }
        viewModel.getResponseFromDb(responseClass.id).observe(this) {
            Glide.with(this)
                .load(it.image)
                .into(ivImageView)
            tvTitle.text = it.title
            tvDescription.text = it.desc
            tvPrice.text = " ₹ " + it.price.toString() + ""
            btnRating.text = it.rate.toString()
            tvCount.text = "(" + responseClass.count.toString() + ")"
        }


        btnEdit.setOnClickListener {
            val intent= Intent(this,AddActivity::class.java)
            intent.putExtra("responseClass",responseClass)
            startActivity(intent)
        }
    }
}