package com.example.ekartapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.ekartapp.R
import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add.*

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    private lateinit var uri: Uri
    private val responseClassListServer = mutableListOf<ResponseClass>()
    private val viewModel by viewModels<ProjectViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val intent=intent
        val response=intent.getParcelableExtra<ResponseClass>("responseClass")
        viewModel.userResponse.observe(this) {
            responseClassListServer.addAll(it)
        }
        if(response!=null){
            uri=response.image.toUri()
            etTitle.setText(response.title)
            Glide.with(this)
                .load(response.image)
                .into(itemImage)
            etItemPrice.setText(response.price.toString())
            etCatagory.setText(response.catagory)
            itemDescription.setText(response.desc)
        }

        uploadBtn.setOnClickListener {
            if (response!=null){
                viewModel.put(response.id,etTitle.text.toString(),etItemPrice.text.toString().toDouble(),itemDescription.text.toString(),etCatagory.text.toString(),response.image,response.rate,response.count)
                val responseClass=ResponseClass(response.id,etTitle.text.toString(),uri.toString(),etItemPrice.text.toString().toDouble(),itemDescription.text.toString(),response.price.toString().toDouble(),response.count,etCatagory.text.toString()
                )
                viewModel.update(responseClass)
            }else{
                viewModel.post(etTitle.text.toString(),etItemPrice.text.toString().toDouble(),itemDescription.text.toString(),etCatagory.text.toString(),uri.toString(),3.4,100)
                val responseClass=ResponseClass(responseClassListServer.size+1,etTitle.text.toString(),uri.toString(),etItemPrice.text.toString().toDouble(),itemDescription.text.toString(),3.4,100,etCatagory.text.toString())
                viewModel.addProduct(responseClass)
            }

        }
        itemImage.setOnClickListener {
            selectImage()
        }
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==10){
            uri= data?.data!!
            Glide.with(itemImage).load(uri).into(itemImage)
           // itemImage.setImageURI(uri)
            Log.d("imageUri",uri.toString())

        }
    }


    private fun selectImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        startActivityForResult(chooserIntent, 10)
    }


}