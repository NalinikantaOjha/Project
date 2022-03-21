package com.example.ekartapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ekartapp.R
import com.example.ekartapp.adapter.OnClick
import com.example.ekartapp.adapter.PostAdapter
import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.retrofitresponse.Status
import com.example.ekartapp.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,OnClick{
    private val viewModel by viewModels<ProjectViewModel>()
    private val responseClassList = mutableListOf<ResponseClass>()
    private var postAdapter: PostAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("nalini",responseClassList.size.toString())
        viewModel.openTheConnection()
        viewModel.delete()
        viewModel.user.observe(this) {
            when (it.status) {
                Status.SUCCESS -> { buildResponseData(it.data) }
                Status.ERROR -> { startActivity(Intent(this,ErrorActivity::class.java)) }
            }
            setRecyclerAdapter() }

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) { filterCollection(s.toString()) }
        })

    }



        private fun setRecyclerAdapter() {

        postAdapter = PostAdapter(this)
            postAdapter!!.setData(responseClassList)
        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = postAdapter}



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
                val responseClass= ResponseClass(id,title,image,price, desc,rateing,count)
                responseClassList.add(responseClass)

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO).launch {

        }

    }

    private fun filterCollection(name: String) {
        var list= mutableListOf<ResponseClass>()
        for (title in responseClassList) {
            if ( title.title.toLowerCase().contains(name.toLowerCase())){
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