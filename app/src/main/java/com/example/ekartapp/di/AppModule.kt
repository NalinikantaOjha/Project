package com.example.ekartapp.di

import android.app.Application
import android.content.Context
import com.example.ekartapp.data.local.ProductDao
import com.example.ekartapp.data.local.ProductDatabase


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule  {


    @Provides
    @Singleton
    fun providesUserDatabase( context: Application): ProductDatabase {
        return ProductDatabase.getContactDatabase(context)
    }
    @Provides
    @Singleton
    fun providesUserContext( context: Application): Context {
        return context
    }
    @Provides
    @Singleton
    fun providesDao(productDatabase: ProductDatabase): ProductDao {
        return productDatabase.getDao()
    }

}