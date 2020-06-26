package com.example.fotofiltry.ui.filter.ui

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
    fun makeBlur(inputPath: String){

    }
    fun makeSharp(inputPath: String){

    }


    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }





}