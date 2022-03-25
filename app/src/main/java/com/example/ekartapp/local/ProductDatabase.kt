package com.example.ekartapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ekartapp.data.ResponseClass

@Database(entities = [ResponseClass::class],version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getDao():ProductDao
    companion object{
        private var instance:ProductDatabase?=null
        fun getContactDatabase(context: Context):ProductDatabase{
            if (instance!=null){
                return instance!!
            }else{
                val builder= Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "productDb"
                )
                builder.fallbackToDestructiveMigration()
                instance=builder.build()
            }
            return instance!!
        }
    }
}