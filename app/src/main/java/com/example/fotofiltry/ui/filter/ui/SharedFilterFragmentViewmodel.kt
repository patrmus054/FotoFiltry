package com.example.fotofiltry.ui.filter.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fotofiltry.Filters
import kotlinx.coroutines.*
import java.io.FileOutputStream

class SharedFilterFragmentViewmodel() : ViewModel() {

    private val _grayScaleBitmap: MutableLiveData<Bitmap?> = MutableLiveData(null)
    private val _blurBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    private val _sharpBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val grayScaleBitmap: LiveData<Bitmap?>
        get() = _grayScaleBitmap
    val blurBitmap: LiveData<Bitmap>
        get() = _blurBitmap
    val sharpBitmap: LiveData<Bitmap>
        get() = _sharpBitmap
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init {
        _isLoading.value = true
    }

    fun makeGrayScale(inputPath: String) {
        Log.e("myapp", "Run grayscale")

        coroutineScope.launch {


            _isLoading.postValue(true)
            delay(2000)
            val modifiedBitmap = Filters.grayscaleFilter(inputPath)

            _grayScaleBitmap.postValue(modifiedBitmap)
            _isLoading.postValue(false)
        }


    }

    fun makeBlur(inputPath: String, context: Context) {
        Log.e("myapp", "Run blur")

        coroutineScope.launch {
            _isLoading.postValue(true)
            delay(2000)
            val modifiedBitmap = Filters.blurFilter(context, inputPath)
            _blurBitmap.postValue(modifiedBitmap)
            _isLoading.postValue(false)
        }
    }

    fun makeSharp(inputPath: String, context: Context) {
        Log.e("myapp", "Run sharp")

        coroutineScope.launch {
            _isLoading.postValue(true)
            delay(2000)
            val modifiedBitmap = Filters.sharpFilter(context, inputPath)
            _sharpBitmap.postValue(modifiedBitmap)
            _isLoading.postValue(false)
        }
    }

    fun saveBitmapToFile(inputPath: String, filterName: String) {
        var bitmap: Bitmap? = null
        when (filterName) {
            "grayScale" -> bitmap = grayScaleBitmap.value!!
            "blur" -> bitmap = blurBitmap.value!!
            "sharp" -> bitmap = sharpBitmap.value!!
        }

        val outputStream = FileOutputStream(inputPath)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()


    }


    override fun onCleared() {
        super.onCleared()
        Log.e("myapp", "cleared")
        viewModelJob.cancel()

    }
}