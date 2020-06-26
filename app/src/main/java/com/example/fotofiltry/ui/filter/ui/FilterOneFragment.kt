package com.example.fotofiltry.ui.filter.ui

import android.graphics.Bitmap
import android.media.ExifInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fotofiltry.R
import com.example.fotofiltry.ui.filter.FilterActivity
import kotlinx.android.synthetic.main.fragment_filter_1.*
import java.io.FileOutputStream

class FilterOneFragment : Fragment() {
   // private lateinit var inputPath: String;
    private lateinit var filteredBitmap: Bitmap
    private val viewModel: SharedFilterFragmentViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_filter_1, container, false)
    }



    override fun onStart(){
        super.onStart()
        img_filter_1.setImageBitmap(viewModel.grayScaleBitmap.value)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.grayScaleBitmap.observe(viewLifecycleOwner, Observer {

            img_filter_1.setImageBitmap(it)


        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        Log.e("myapp","Start gray")
        viewModel.makeGrayScale(FilterActivity.inputPath)


    }

    fun saveBitmapToFile(inputPath: String, bitmap: Bitmap) {
        val outputPath = "dd"
        val outputStream = FileOutputStream(outputPath)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        val exifI = ExifInterface(inputPath)
        val newExifI = ExifInterface(outputPath)

    }
}