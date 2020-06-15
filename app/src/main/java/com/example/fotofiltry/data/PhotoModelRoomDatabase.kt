package com.example.fotofiltry.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(PhotoModel::class), version = 5, exportSchema = false)
abstract class PhotoModelRoomDatabase : RoomDatabase(){

    abstract fun PhotoDAO(): PhotoModelDAO
    companion object{
        @Volatile
        private var INSTANCE: PhotoModelRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PhotoModelRoomDatabase{
            val tempInstance = PhotoModelRoomDatabase.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoModelRoomDatabase::class.java,
                    "photo_database"
                ).addCallback(PhotoDatabaseCallback(scope)).build()
                PhotoModelRoomDatabase.INSTANCE = instance
                return instance
            }
        }
    }

    private class PhotoDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.PhotoDAO())
                }
            }
        }

        suspend fun populateDatabase(photoDao: PhotoModelDAO) {
            photoDao.deleteAll()
//            photoDao.insert(PhotoModel(0,"photo1","30.04.2020"))
//            photoDao.insert(PhotoModel(1,"photo2","31.04.2020"))
//            photoDao.insert(PhotoModel(2,"photo3","29.04.2020"))
        }
    }
}
