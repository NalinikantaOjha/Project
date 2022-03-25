package com.example.ekartapp.viewmodeltest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ekartapp.data.ResponseClass
import com.example.ekartapp.repository.ProjectRepository
import com.example.ekartapp.viewmodel.ProjectViewModel
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class ViewModelTest {

    @MockK
    lateinit var repository: ProjectRepository
    private lateinit var viewModel:ProjectViewModel
     @MockK
    lateinit var  product:ResponseClass
    @MockK
    lateinit var  productList:MutableList<ResponseClass>
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel= ProjectViewModel(repository)
        viewModel = spyk(ProjectViewModel(repository))

    }
    @Test
    fun is_searchData_isNotNull_true(){
        coEvery { repository.getResponseFromDb(2).value}returns product
        val searchData=viewModel.getResponseFromDb(2).value
        Truth.assertThat(searchData).isNotNull()
    }

    @Test
    fun is_searchData_isEqualTo_true(){
        coEvery { repository.getResponseFromDb(2).value}returns product
        val searchData=viewModel.getResponseFromDb(2).value
        Truth.assertThat(searchData).isEqualTo(product)
    }
    @Test
    fun createProduct_functionCall(){
        coEvery { repository.addProduct(product) } returns Unit
        runBlocking { viewModel.addProduct(product) }
        coVerify { repository.addProduct(product) }
    }

    @Test
    fun addAllProduct_functionCall(){
        coEvery { repository.addAllProduct(productList) } returns Unit
        runBlocking { viewModel.addAllProduct(productList) }
        coVerify { repository.addAllProduct(productList) }
    }
    @Test
    fun updateContact_functionCall(){
        coEvery { repository.update(product) } returns Unit
        runBlocking { viewModel.update(product) }
        coVerify { repository.update(product) }
    }
    //

    @Test
    fun openTheConnection_verifyingFunctionCall(){
        coEvery {
            repository.openTheConnection()
        }returns Unit
        viewModel.openTheConnection()
        coVerify { repository.openTheConnection() }
    }
    @Test
    fun postData_verifyingFunctionCall(){
        coEvery {
            repository.postData("title",100.0,"description","category","image",3.0,100)
        }returns Unit
        viewModel.post("title",100.0,"description","category","image",3.0,100)
        coVerify { repository.postData("title",100.0,"description","category","image",3.0,100) }
    }
    @Test
    fun putData_verifyingFunctionCall(){
        coEvery {
            repository.putData(1,"title",100.0,"description","category","image",3.0,100)
        }returns Unit
        viewModel.put(1,"title",100.0,"description","category","image",3.0,100)
        coVerify { repository.putData(1,"title",100.0,"description","category","image",3.0,100) }
    }
    @Test
    fun delete_verifyingFunctionCall(){
        coEvery {
            repository.Delete("1")
        }returns Unit
        viewModel.update("1")
        coVerify { repository.Delete("1") }
    }

}