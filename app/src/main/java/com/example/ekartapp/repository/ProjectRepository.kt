package com.example.ekartapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.retrofitresponse.Resource
import com.example.ekartapp.retrofitresponse.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class ProjectRepository @Inject constructor (){
       private val responseClassBuffer= MutableLiveData<Resource<StringBuffer>>()
       val  userBuffer: LiveData<Resource<StringBuffer>> get()=responseClassBuffer
       private val responseHandler: ResponseHandler = ResponseHandler()

    fun openTheConnection(){
        val stringBuffer = StringBuffer()
        CoroutineScope(Dispatchers.IO).launch {
            var url: URL? = null
            try {
                url = URL("https://fakestoreapi.com/products")
                //Open the connection and connect to server
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod="GET"

                    //stream  of data from the server
                val inputStream = urlConnection.inputStream
                //read the stream of data received from the server
                val inputStreamReader = InputStreamReader(inputStream)
                // inputStreamReader.read() will give one element at a time so data will have the first element
                var data = inputStreamReader.read()
                // StringBuffer class is used to build the json response int the string format
                // when data is -1 we reached teh end of the response
                while (data != -1) {
                    // the data will be in byte format so we are cast it to char
                    val ch = data.toChar()
                    stringBuffer.append(ch)
                    data = inputStreamReader.read() }
                // Now the response is available in jsom format
                    // buildResponseData(stringBuffer)
                responseClassBuffer.postValue(responseHandler.handleSuccess(stringBuffer))
            } catch (e: Exception) {
                Log.d("nalinierror",e.toString())
                responseHandler.handleException<Int>(e)
                e.printStackTrace()
            }
        }

    }

}