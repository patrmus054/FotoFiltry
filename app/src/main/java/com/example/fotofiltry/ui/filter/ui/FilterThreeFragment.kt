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
import kotlinx.android.synthetic.main.fragment_filter_3.*

class FilterThreeFragment : Fragment() {
    private val viewModel: SharedFilterFragmentViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_filter_3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.sharpBitmap.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                img_filter_3.setImageBitmap(it)

            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar3.visibility = View.VISIBLE
            } else {
                progressBar3.visibility = View.GONE
            }
        })


    }

    override fun onStop() {
        super.onStop()
        viewModel.isLoading.removeObservers(viewLifecycleOwner)
        viewModel.sharpBitmap.removeObservers(viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.e("myapp", "Start sharp")

        if (viewModel.sharpBitmap.value == null) {
            viewModel.makeSharp(FilterActivity.inputPath, requireContext())

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.savePhoto) {
            Log.e("myapp", "Start saving")
            viewModel.saveBitmapToFile(FilterActivity.inputPath, "sharp")
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