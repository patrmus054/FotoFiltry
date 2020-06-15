package com.example.fotofiltry.ui.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FilterViewModel(application: Application) :   AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Filter View Model"
    }
    val text: LiveData<String> = _text
}