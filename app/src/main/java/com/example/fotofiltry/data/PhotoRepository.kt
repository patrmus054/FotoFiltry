package com.example.fotofiltry.data

import androidx.lifecycle.LiveData

class PhotoRepository(private val PhotoDao: PhotoModelDAO) {

    val allPhotos: LiveData<List<PhotoModel>> = PhotoDao.getAllPhotos()

    suspend fun insert(photo: PhotoModel) {
        PhotoDao.insert(photo)
    }
}