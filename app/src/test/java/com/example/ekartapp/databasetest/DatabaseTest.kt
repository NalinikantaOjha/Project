package com.example.ekartapp.databasetest

import android.content.Context
import androidx.room.Room
import com.example.ekartapp.local.ProductDao
import com.example.ekartapp.local.ProductDatabase
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class DatabaseTest {
    private lateinit var db: ProductDatabase
    private lateinit var dao: ProductDao
    @MockK
    lateinit var context : Context

    @MockK
    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        db = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java).build()
        dao = db.getDao()
    }
    @Test
    fun getContactDatabase_instanceCheck_true(){
        every { ProductDatabase.getContactDatabase(context) } returns db
        val result = ProductDatabase.getContactDatabase(context)
        Truth.assertThat(result).isInstanceOf(ProductDatabase::class.java)
    }
}