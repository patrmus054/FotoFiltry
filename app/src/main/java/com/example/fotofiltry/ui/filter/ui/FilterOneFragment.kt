package com.example.fotofiltry.ui.filter.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fotofiltry.R
import com.example.fotofiltry.ui.filter.FilterActivity
import kotlinx.android.synthetic.main.fragment_filter_1.*

//Grayscale fragment
class FilterOneFragment : Fragment() {


    private val viewModel: SharedFilterFragmentViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_filter_1, container, false)
    }


    override fun onStop() {
        super.onStop()
        viewModel.grayScaleBitmap.removeObservers(viewLifecycleOwner)
        viewModel.isLoading.removeObservers(viewLifecycleOwner)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.grayScaleBitmap.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                img_filter_1.setImageBitmap(it)

            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.e("myapp", "Start gray")

        if (viewModel.grayScaleBitmap.value == null) {
            viewModel.makeGrayScale(FilterActivity.inputPath)

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.savePhoto) {
            Log.e("myapp", "Start saving")
            viewModel.saveBitmapToFile(FilterActivity.inputPath, "grayScale")
            Log.e("myapp", "End saving")
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            val activity = activity as FilterActivity
            activity.backToHome()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {


        viewModelStore.clear()
        super.onPause()
    }


}