package com.example.fotofiltry.data;

import androidx.room.*


@Dao
interface PhotoModelDAO {
    @Query("SELECT * from photo_table ORDER BY title ASC")
    fun getAllPhotos(): List<PhotoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photoModel: PhotoModel)

    @Update
    suspend fun update(photo: PhotoModel)

    @Query("DELETE FROM photo_table")
    suspend fun deleteAll()
}
