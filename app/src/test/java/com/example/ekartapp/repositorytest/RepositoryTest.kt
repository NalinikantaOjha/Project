package com.example.ekartapp.repositorytest

import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.local.ProductDao
import com.example.ekartapp.repository.ProjectRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositoryTest {
    @MockK
   lateinit var dao:ProductDao
    private lateinit var repository:ProjectRepository
    @MockK
    lateinit var product:ResponseClass
    @MockK var productlist= mutableListOf<ResponseClass>()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        coEvery { dao.getAllProduct().value }returns productlist
        repository= ProjectRepository(dao)

    }
    @Test
    fun createProduct_functionCall(){
        coEvery { dao.addProduct(product) } returns Unit
        runBlocking { repository.addProduct(product) }
        coVerify { dao.addProduct(product) }
    }


    @Test
    fun updateContact_functionCall(){
        coEvery { dao.update(product) } returns Unit
        runBlocking { repository.update(product) }
        coVerify { dao.update(product) }
    }

    @Test
    fun is_searchData_isEqualTo_true(){
        coEvery { dao.getResponse(2).value}returns product
        val searchData=repository.getResponseFromDb(2).value
        assertThat(searchData).isEqualTo(product)
    }
    //
    @Test
    fun openTheUrl(){
       val rep= repository.openTheConnection()
        assertThat(rep).isNotNull()
    }
    @Test
    fun post(){
        val rep= repository.postData("title",100.0,"description","category","image",3.0,100)
        assertThat(rep).isNotNull()
    }
    @Test
    fun put(){
        val rep= repository.putData(1,"title",100.0,"description","category","image",3.0,100)
        assertThat(rep).isNotNull()
    }
    @Test
    fun delete(){
        val rep= repository.Delete("1")
        assertThat(rep).isNotNull()
    }
}