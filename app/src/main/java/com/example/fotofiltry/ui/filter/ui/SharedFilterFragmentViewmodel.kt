package com.example.fotofiltry.ui.filter.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fotofiltry.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SharedFilterFragmentViewmodel : ViewModel() {

    private val _grayScaleBitmap:MutableLiveData<Bitmap> = MutableLiveData()
    private val _blurBitmap:MutableLiveData<Bitmap> = MutableLiveData()
    private val _sharpBitmap:MutableLiveData<Bitmap> = MutableLiveData()
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData(false)
    val grayScaleBitmap:LiveData<Bitmap>
    get() = _grayScaleBitmap
    val blurBitmap:LiveData<Bitmap>
    get() = _blurBitmap
    val sharpBitmap:LiveData<Bitmap>
    get() = _sharpBitmap
    val isLoading:LiveData<Boolean>
    get() = _isLoading

    fun makeGrayScale(inputPath:String){
        _isLoading.value = true
        if(grayScaleBitmap.value == null) {


            GlobalScope.launch(Dispatchers.Main) {

                val modifiedBitmap = Filters.grayscaleFilter(inputPath)
                _grayScaleBitmap.value = modifiedBitmap
            }

        }
        _isLoading.value = false

    }
    fun makeBlur(inputPath: String){

    }
    fun makeSharp(inputPath: String){

    }



}