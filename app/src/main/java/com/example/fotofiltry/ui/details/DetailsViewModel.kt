package com.example.fotofiltry.ui.details

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.fotofiltry.data.PhotoModel
import com.example.fotofiltry.data.PhotoModelRoomDatabase
import com.example.fotofiltry.data.PhotoRepository
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application):AndroidViewModel(application){
    private val repository: PhotoRepository
    private val _photo:MutableLiveData<PhotoModel> = MutableLiveData()
    val photo:LiveData<PhotoModel>
    get() = _photo
    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading:LiveData<Boolean>
    get() = _isLoading
    private val _isDeleted = MutableLiveData<Boolean>(false)
    val isDeleted:LiveData<Boolean>
    get() = _isDeleted
    init {
        val photosDAO = PhotoModelRoomDatabase.getDatabase(application, viewModelScope).PhotoDAO()
        repository = PhotoRepository(photosDAO)
    }

    fun deletePhoto(id:Int){
        viewModelScope.launch {
            repository.deletePhoto(id)
            _isDeleted.value = true
        }
    }

    fun getDetailsById(id:Int){
        viewModelScope.launch {
            _isLoading.value = true
            _photo.value = (repository.getDetailsById(id))
            _isLoading.value = false
        }
    }


}