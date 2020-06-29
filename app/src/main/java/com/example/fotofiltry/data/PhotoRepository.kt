package com.example.fotofiltry.data

import androidx.lifecycle.LiveData

class PhotoRepository(private val PhotoDao: PhotoModelDAO) {


    fun getAllPhotos():List<PhotoModel>{
        return PhotoDao.getAllPhotos()
    }
    suspend fun insert(photo: PhotoModel) {
        PhotoDao.insert(photo)
    }
    suspend fun update(photo: PhotoModel){
        PhotoDao.update(photo)
    }

    suspend fun deletePhoto(id:Int){
        PhotoDao.deleteById(id)
    }
    suspend fun getDetailsById(id:Int):PhotoModel{
       return PhotoDao.getDetailsById(id)
    }
}