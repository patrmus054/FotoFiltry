package com.example.fotofiltry.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fotofiltry.R
import com.example.fotofiltry.data.Filter
import com.example.fotofiltry.data.PhotoModel

class HomeViewModel: ViewModel() {

    var _item: MutableLiveData<List<PhotoModel>> = MutableLiveData()
    val item: LiveData<List<PhotoModel>> get() = _item

    fun getPhotos(){

        val result: MutableList<PhotoModel> = ArrayList<PhotoModel>()
        result.add( PhotoModel(0,"photo1","30.04.2020", Filter.FILTERNAME1))
        result.add( PhotoModel(1,"photo2","31.04.2020", Filter.FILTERNAME2))
        result.add( PhotoModel(2,"photo3","29.04.2020", Filter.FILTERNAME3))
        //todo handling data gathering form bd and assign them to var result
        //temp hard implementation

        _item.value = result
    }
}