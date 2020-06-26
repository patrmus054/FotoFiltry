package com.example.fotofiltry.ui.filter.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fotofiltry.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SharedFilterFragmentViewmodel() : ViewModel() {

    private val _grayScaleBitmap:MutableLiveData<Bitmap?> = MutableLiveData(null)
    private val _blurBitmap:MutableLiveData<Bitmap> = MutableLiveData()
    private val _sharpBitmap:MutableLiveData<Bitmap> = MutableLiveData()
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData(true)
    val grayScaleBitmap:LiveData<Bitmap?>
    get() = _grayScaleBitmap
    val blurBitmap:LiveData<Bitmap>
    get() = _blurBitmap
    val sharpBitmap:LiveData<Bitmap>
    get() = _sharpBitmap
    val isLoading:LiveData<Boolean>
    get() = _isLoading

    fun makeGrayScale(inputPath:String){
        Log.e("myapp", "Run grayscale")

            GlobalScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                val modifiedBitmap = Filters.grayscaleFilter(inputPath)

                _grayScaleBitmap.postValue(modifiedBitmap)
                _isLoading.postValue(false)
            }


    }
    fun makeBlur(inputPath: String,context:Context){
        Log.e("myapp","Run blur")

        GlobalScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val modifiedBitmap = Filters.blurFilter(context,inputPath)
            _blurBitmap.postValue(modifiedBitmap)
            _isLoading.postValue(false)
        }
    }
    fun makeSharp(inputPath: String){

    }








}