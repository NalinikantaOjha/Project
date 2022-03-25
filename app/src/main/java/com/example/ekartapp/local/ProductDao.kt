package com.example.ekartapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ekartapp.data.ResponseClass

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend    fun addAllProducts(listOfProduct:List<ResponseClass>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product:ResponseClass)
    @Query( "select * from ResponseClass")
    fun getAllProduct():LiveData<List<ResponseClass>>
    @Query( "delete from ResponseClass")
    suspend fun deleteAll()
    @Update
    suspend fun update(responseClass: ResponseClass)
    @Query("SELECT * FROM ResponseClass WHERE id= :id")
    fun getResponse(id: Int): LiveData<ResponseClass>
}
