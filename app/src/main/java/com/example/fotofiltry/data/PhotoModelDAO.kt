package com.example.fotofiltry.data;

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query;

@Dao
interface PhotoModelDAO {
    @Query("SELECT * from photo_table ORDER BY title ASC")
    fun getAllPhotos(): List<PhotoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photoModel: PhotoModel)

    @Query("DELETE FROM photo_table")
    suspend fun deleteAll()
}
