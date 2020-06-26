package com.example.fotofiltry.ui.filter.ui

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
import kotlinx.android.synthetic.main.fragment_filter_2.*
import kotlinx.android.synthetic.main.fragment_filter_3.*

class FilterThreeFragment : Fragment() {
    private val viewModel: SharedFilterFragmentViewmodel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
}