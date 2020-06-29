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

    @Query("DELETE FROM photo_table WHERE photoId = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM photo_table WHERE photoID = :id")
    suspend fun getDetailsById(id:Int):PhotoModel

}
