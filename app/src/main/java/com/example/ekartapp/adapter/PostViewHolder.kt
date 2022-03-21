package com.example.ekartapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ekartapp.data.ResponseClass
import kotlinx.android.synthetic.main.item_layout.view.*

class PostViewHolder(itemView:View,val onClick: OnClick):RecyclerView.ViewHolder(itemView) {
    fun setData(responseClass: ResponseClass) {
        itemView.apply {
            cCardView.setOnClickListener {
                onClick.onClickProduct(responseClass)
            }
            tvTitle.text = responseClass.title
//            tvId.text = responseClass.id.toString() + ""
            Glide.with(context)
                .load(responseClass.image)
                .into(ivImageView)
            tvPrice.text = " ₹ "+responseClass.price.toString() + ""

        }


    }
}