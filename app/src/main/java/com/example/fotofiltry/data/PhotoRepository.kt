package com.example.fotofiltry.data

import androidx.lifecycle.LiveData

class PhotoRepository(private val PhotoDao: PhotoModelDAO) {


    fun getAllPhotos():List<PhotoModel>{
        return PhotoDao.getAllPhotos()
    }
    suspend fun insert(photo: PhotoModel) {
        PhotoDao.insert(photo)
    }
}