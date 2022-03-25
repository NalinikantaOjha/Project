package com.example.ekartapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.local.ProductDao
import com.example.ekartapp.retrofitresponse.Resource
import com.example.ekartapp.retrofitresponse.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


class ProjectRepository @Inject constructor (val productDao: ProductDao){
     val responseClass= productDao.getAllProduct()
    val  response: LiveData<List<ResponseClass>> get()=responseClass
       private val responseClassBuffer= MutableLiveData<Resource<StringBuffer>>()
       val  userBuffer: LiveData<Resource<StringBuffer>> get()=responseClassBuffer
       private val responseHandler: ResponseHandler = ResponseHandler()


    suspend fun addAllProduct(list:List<ResponseClass>){
        productDao.addAllProducts(list)
    }
    suspend fun addProduct(list:ResponseClass){
        productDao.addProduct(list)
    }
    suspend fun deleteAll(){
        productDao.deleteAll()
    }
    suspend fun update(responseClass: ResponseClass){
        productDao.update(responseClass)
    }
    fun getResponseFromDb(id:Int):LiveData<ResponseClass>{
       return productDao.getResponse(id)
    }
    fun openTheConnection(){
        val stringBuffer = StringBuffer()
        CoroutineScope(Dispatchers.IO).launch {
            var url: URL? = null
            try {
                url = URL("https://fakestoreapi.com/products")
                //Open the connection and connect to server
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                val a: InputStream = BufferedInputStream(urlConnection.inputStream)
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
                    data = inputStreamReader.read()
                }
                // Now the response is available in jsom format
                // buildResponseData(stringBuffer)
                responseClassBuffer.postValue(responseHandler.handleSuccess(stringBuffer))
            } catch (e: Exception) {
                Log.d("nalinierror", e.toString())
                responseHandler.handleException<Int>(e)
                e.printStackTrace()
            }
        }

    }
    fun putData(id:Int,title:String,price:Double,description:String,category:String,image:String,rate1:Double,count:Int){
        val rate=JSONObject()
        val params = JSONObject()
        rate.put("rate",rate1)
        rate.put("count",count)
        params.put("title",title)
        params.put("price",price)
        params.put("description",description)
        params.put("category",category)
        params.put("image",image)
        params.put("rating",rate)
        val paramsString=params.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://reqres.in/api/users/$id")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.doOutput = true
                urlConnection.requestMethod = "PUT"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                // The format of the content we're sending to the server
                urlConnection.setRequestProperty("Accept", "application/json")
                // The format of response we want to get from the server
                urlConnection.doInput = true
                urlConnection.doOutput = true
                // Send the JSON we created
                val outputStreamWriter = OutputStreamWriter(urlConnection.outputStream)
                outputStreamWriter.write(paramsString)
                outputStreamWriter.flush()
                Log.d("naliiedit",urlConnection.responseCode.toString())
            }catch (e:Exception){
                e.stackTrace
            }
        }
    }
    fun postData(title:String,price:Double,description:String,category:String,image:String,rate1:Double,count:Int) {
        val rate=JSONObject()
        val params = JSONObject()
        rate.put("rate",rate1)
        rate.put("count",count)
        params.put("title",title)
        params.put("price",price)
        params.put("description",description)
        params.put("category",category)
        params.put("image",image)
        params.put("rating",rate)
        val paramsString=params.toString()
        CoroutineScope(Dispatchers.IO).launch {
            var url: URL? = null
            try {
                url = URL("https://fakestoreapi.com/products")
                //Open the connection and connect to server
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "POST"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                // The format of the content we're sending to the server
                urlConnection.setRequestProperty("Accept", "application/json")
                // The format of response we want to get from the server
                urlConnection.doInput = true
                urlConnection.doOutput = true
            // Send the JSON we created
            val outputStreamWriter = OutputStreamWriter(urlConnection.outputStream)
            outputStreamWriter.write(paramsString)
            outputStreamWriter.flush()
            } catch (e: Exception) {
                Log.d("nalinierror", e.toString())
                responseHandler.handleException<Int>(e)
                e.printStackTrace()
            }
        }

    }
    fun Delete(id:String){
        val url = URL("https://fakestoreapi.com/products/$id")
        val httpCon = url.openConnection() as HttpURLConnection
        httpCon.doOutput = true
        httpCon.requestMethod = "DELETE"
        httpCon.connect()
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
/*
 val out = OutputStreamWriter(httpCon.outputStream)
            out.write("name")
 */
//    fun rawJSON() {
//        // Create JSON using JSONObject
//        val jsonObject = JSONObject()
//        jsonObject.put("email_id", "dipu843302@gmail.com")
//        jsonObject.put("password", "123456789")
//
//
//        // Convert JSONObject to String
//        val jsonObjectString = jsonObject.toString()
//
//        GlobalScope.launch(Dispatchers.IO) {
//            val url = URL("https://dummy.restapiexample.com/api/v1/create")
//            val httpURLConnection = url.openConnection() as HttpURLConnection
//            httpURLConnection.requestMethod = "POST"
//            httpURLConnection.setRequestProperty(
//                "Content-Type",
//                "application/json"
//            ) // The format of the content we're sending to the server
//            httpURLConnection.setRequestProperty(
//                "Accept",
//                "application/json"
//            ) // The format of response we want to get from the server
//            httpURLConnection.doInput = true
//            httpURLConnection.doOutput = true
//
//            // Send the JSON we created
//            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
//            outputStreamWriter.write(jsonObjectString)
//            outputStreamWriter.flush()
//
//            // Check if the connection is successful
//            val responseCode = httpURLConnection.responseCode
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                val response = httpURLConnection.inputStream.bufferedReader()
//                    .use { it.readText() }
//                // defaults to UTF-8

//
//            } else {
//                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
//            }
//
//        }
//
//    }