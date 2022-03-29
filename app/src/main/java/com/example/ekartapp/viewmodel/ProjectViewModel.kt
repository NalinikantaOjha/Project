package com.example.ekartapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekartapp.data.model.ResponseClass
import com.example.ekartapp.data.repository.ProjectRepository
import com.example.ekartapp.retrofitresponse.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(private val projectRepository: ProjectRepository):ViewModel() {
    val user:LiveData<Resource<StringBuffer>> get()=projectRepository.userBuffer
    val userResponse:LiveData<List<ResponseClass>> get()=projectRepository.response

    fun addAllProduct(list:List<ResponseClass>){
        viewModelScope.launch (Dispatchers.IO){
        projectRepository.addAllProduct(list)}
    }
    fun update(responseClass: ResponseClass){
        viewModelScope.launch (Dispatchers.IO){
            projectRepository.update(responseClass)
        }
    }
    fun addProduct(responseClass: ResponseClass){
        viewModelScope.launch (Dispatchers.IO){
            projectRepository.addProduct(responseClass)
        }
    }
    fun getResponseFromDb(id:Int):LiveData<ResponseClass>{
          return  projectRepository.getResponseFromDb(id)

    }
    fun openTheConnection(){
        Log.d("naliniV","fUNCTION Called")
        viewModelScope.launch(Dispatchers.IO){
        projectRepository.openTheConnection()
        }
    }
fun post(title:String,price:Double,description:String,category:String,image:String,rate:Double,count:Int){
    viewModelScope.launch (Dispatchers.IO){
        projectRepository.postData(title,price,description,category,image,rate,count)
    }
}

    fun update(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
           projectRepository.Delete(id)
        }
    }
    fun put(id:Int,title:String,price:Double,description:String,category:String,image:String,rate:Double,count:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.putData(id, title, price, description, category, image, rate, count)
        }
    }

}