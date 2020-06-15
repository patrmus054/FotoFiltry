package com.example.fotofiltry.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.fotofiltry.R
import com.example.fotofiltry.data.Filter
import com.example.fotofiltry.data.PhotoModel
import com.example.fotofiltry.data.PhotoModelRoomDatabase
import com.example.fotofiltry.data.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    var _item: MutableLiveData<List<PhotoModel>> = MutableLiveData()
    var item: LiveData<List<PhotoModel>> get() = _item
    private val repository: PhotoRepository

    init {
        val photosDAO = PhotoModelRoomDatabase.getDatabase(application, viewModelScope).PhotoDAO()
        repository = PhotoRepository(photosDAO)
        item = repository.allPhotos
    }

    fun insert(photo: PhotoModel){
        Log.w("insert", "Jestem0" + photo.location)
        viewModelScope.launch(Dispatchers.IO) {
            Log.w("insert", "Jestem1" + photo.location)
        repository.insert(photo)
            Log.w("insert", "Jestem2" + photo.location)
        }
        item = repository.allPhotos
        Log.w("insert", "Jestem3" + photo.location)
    }

//    fun getPhotos(){
//        val result: MutableList<PhotoModel> = ArrayList<PhotoModel>()
//        result.add( PhotoModel(0,"photo1","30.04.2020"))
//        result.add( PhotoModel(1,"photo2","31.04.2020"))
//        result.add( PhotoModel(2,"photo3","29.04.2020"))
//        //todo handling data gathering form bd and assign them to var result
//        //temp hard implementation
//
//        _item.value = result
//    }
}