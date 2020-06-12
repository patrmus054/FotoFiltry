package com.example.fotofiltry.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(PhotoModel::class), version = 1, exportSchema = false)
abstract class PhotoModelRoomDatabase : RoomDatabase(){

    abstract fun PhotoDAO(): PhotoModelDAO
    companion object{
        @Volatile
        private var INSTANCE: PhotoModelRoomDatabase? = null

        fun getDatabase(context: Context): PhotoModelRoomDatabase{
            val tempInstance = PhotoModelRoomDatabase.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoModelRoomDatabase::class.java,
                    "photo_database"
                ).build()
                PhotoModelRoomDatabase.INSTANCE = instance
                return instance
            }
        }
    }
}
