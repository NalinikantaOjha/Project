package com.example.ekartapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ekartapp.R
import com.example.ekartapp.adapter.PostAdapter
import com.example.ekartapp.data.ResponseClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val responseClassList = mutableListOf<ResponseClass>()
    private var postAdapter: PostAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch {
            var url: URL? = null
            try {
                url = URL("https://fakestoreapi.com/products")

                val urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = urlConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)
                var data = inputStreamReader.read()
                val stringBuffer = StringBuffer()
                while (data != -1) {
                    val ch = data.toChar()
                    stringBuffer.append(ch)
                    data = inputStreamReader.read()
                }
                buildResponseData(stringBuffer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        setRecyclerAdapter()
    }

    private fun buildResponseData(stringBuffer: StringBuffer) {
        val json = stringBuffer.toString()
        try {
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val eachJsonObject = jsonArray.getJSONObject(i)
                val id = eachJsonObject.getInt("id")
                val title = eachJsonObject.getString("title")
                val image=eachJsonObject.getString("image")
                val responseClass= ResponseClass(id,title,image)
                responseClassList.add(responseClass)
            }
            runOnUiThread {
                updateAdapter()
            }
            Log.d("Lloyd", "Response built")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    private fun setRecyclerAdapter() {
        postAdapter = PostAdapter(responseClassList)
        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = postAdapter
    }
    private fun updateAdapter() {
        runOnUiThread { postAdapter!!.updateRecyclerViewList(responseClassList) }

    }
}