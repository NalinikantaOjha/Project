package com.example.ekartapp.repositorytest

import com.example.ekartapp.repository.ProjectRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RepositoryTest {
    val repository=ProjectRepository()
    val url="https://fakestoreapi.com/productsa"
    @Test
    fun u(){
       val rep= repository.openTheConnection()
        assertThat(rep).isNotNull()
    }
}