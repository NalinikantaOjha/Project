package com.example.ekartapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ekartapp.adapter.iterface.OnClick
import com.example.ekartapp.adapter.iterface.OnEdit
import com.example.ekartapp.data.ResponseClass
import kotlinx.android.synthetic.main.item_layout.view.*

class PostViewHolder(itemView:View, private val onClick: OnClick, private val onEdit: OnEdit):RecyclerView.ViewHolder(itemView) {
    fun setData(responseClass: ResponseClass) {

        itemView.apply {

            cCardView.setOnClickListener {
                onClick.onClickProduct(responseClass)
            }

            tvTitle.text = responseClass.title

            Glide.with(this)
                .load(responseClass.image)
                .into(ivImageView2)


            tvPrice.text = " ₹ "+responseClass.price.toString() + ""
            ivEdit.setOnClickListener {
                onEdit.onEditProduct(responseClass)
            }
        }


    }
}