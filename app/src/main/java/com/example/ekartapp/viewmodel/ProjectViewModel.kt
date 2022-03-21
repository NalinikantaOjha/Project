package com.example.ekartapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekartapp.repository.ProjectRepository
import com.example.ekartapp.retrofitresponse.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
class ProjectViewModel @Inject constructor(private val projectRepository: ProjectRepository):ViewModel() {
    val user:LiveData<Resource<StringBuffer>> get()=projectRepository.userBuffer
    fun openTheConnection(){
        Log.d("naliniV","fUNCTION Called")
        viewModelScope.launch(Dispatchers.IO){
        projectRepository.openTheConnection()
        }
    }
    fun delete(){
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL("https://reqres.in/api/users/2")
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod = "DELETE"
          //  httpsURLConnection.doInput = true
          //  httpsURLConnection.doOutput = false

            // Check if the connection is successful
            val responseCode = httpsURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {

            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }
    }
}