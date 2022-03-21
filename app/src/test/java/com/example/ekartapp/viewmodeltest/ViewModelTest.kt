package com.example.ekartapp.viewmodeltest

import android.hardware.camera2.CaptureResult
import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ekartapp.repository.ProjectRepository
import com.example.ekartapp.retrofitresponse.Resource
import com.example.ekartapp.retrofitresponse.ResponseHandler
import com.example.ekartapp.retrofitresponse.Status
import com.example.ekartapp.viewmodel.ProjectViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import java.net.SocketException

@RunWith(AndroidJUnit4::class)

class ViewModelTest {

    @MockK
    lateinit var repository: ProjectRepository
    private lateinit var viewModel:ProjectViewModel

     @MockK
    lateinit var  status:Status
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel= ProjectViewModel(repository)
        viewModel = spyk(ProjectViewModel(repository))

    }

    @Test
    fun openTheConnection_verifyingFunctionCall(){
        coEvery {
            repository.openTheConnection()
        }returns Unit
        viewModel.openTheConnection()
        coVerify { repository.openTheConnection() }
    }
//    @Test
//    fun openTheConnection_verifyingFunctionCall2(){
//        val captureData= mockk<LiveData<Resource<StringBuffer>>>()
//        val stringBuffer=StringBuffer()
//        coEvery {
//            repository.openTheConnection()
//        }coAnswers  {
//            captureData
//        }
//        assertThat(captureData).isEqualTo(viewModel.openTheConnection())
//    }

}