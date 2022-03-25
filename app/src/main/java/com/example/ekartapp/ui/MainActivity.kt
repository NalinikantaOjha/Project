package com.example.ekartapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ekartapp.R
import com.example.ekartapp.adapter.PostAdapter
import com.example.ekartapp.adapter.iterface.OnClick
import com.example.ekartapp.adapter.iterface.OnEdit
import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.retrofitresponse.Status
import com.example.ekartapp.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , OnClick ,OnEdit{
    private val viewModel by viewModels<ProjectViewModel>()
    private val responseClassList = mutableListOf<ResponseClass>()
    private val responseClassListServer = mutableListOf<ResponseClass>()
    private var postAdapter: PostAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.openTheConnection()
        viewModel.user.observe(this) {
            when (it.status) {
                Status.SUCCESS -> { buildResponseData(it.data) }
                Status.ERROR -> { startActivity(Intent(this,ErrorActivity::class.java)) }
            }
        }

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) { filterCollection(s.toString()) }
        })

        btnAdd.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }

   }



        private fun setRecyclerAdapter() {
        postAdapter = PostAdapter(this,this)
            postAdapter!!.setData(responseClassList)
        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = postAdapter
        Progressbar.visibility= View.GONE}




    override fun onClickProduct(responseClass: ResponseClass) {
        val intent=Intent(this,ViewActivity::class.java)
        intent.putExtra("response",responseClass)
        startActivity(intent) }
    private fun buildResponseData(stringBuffer: StringBuffer?) {
        // Building a JSON object from the received string
        val json = stringBuffer.toString()
        try {
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val eachJsonObject = jsonArray.getJSONObject(i)
                val id = eachJsonObject.getInt("id")
                val title = eachJsonObject.getString("title")
                val image=eachJsonObject.getString("image")
                val price=eachJsonObject.getDouble("price")
                val desc=eachJsonObject.getString("description")
                val rate=eachJsonObject.getJSONObject("rating")
                val rateing=rate.getDouble("rate")
                val count=rate.getInt("count")
                val category=eachJsonObject.getString("category")
                val responseClass= ResponseClass(id,title,image,price, desc,rateing,count,category
                )
                responseClassListServer.add(responseClass)
            }
            viewModel.addAllProduct(responseClassListServer)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("nalinidatafromroom","it.size.toString()")
        viewModel.userResponse.observe(this) {
            Log.d("nalinidatafromroom", it.size.toString())
            responseClassList.clear()
            responseClassList.addAll(it)
            setRecyclerAdapter()
        }
    }

    private fun filterCollection(name: String) {
        val list= mutableListOf<ResponseClass>()
        for (title in responseClassList) {
            if ( title.title.lowercase().contains(name.lowercase())){
                list.add(title)
            }
        }
        if (list.isEmpty()){
            val toast= Toast.makeText(this,"No product  found", Toast.LENGTH_LONG)
            toast.show()
        }else{
           // postAdapter?.FILTER(list)
            postAdapter?.setData(list)
        }
    }

    override fun onEditProduct(responseClass: ResponseClass) {
     val intent=Intent(this,AddActivity::class.java)
        intent.putExtra("responseClass",responseClass)
        startActivity(intent)
         }
}

/*
"id":1,"title":"Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops","price":109.95,
"description":"Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
"category":"men's clothing","image":"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
"rating":{"rate":3.9,"count":120}

{"id":1,"title":"Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops","price":109.95,
"description":"Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
"category":"men's clothing","image":"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
"rating":{"rate":3.9,"count":120}}
 */