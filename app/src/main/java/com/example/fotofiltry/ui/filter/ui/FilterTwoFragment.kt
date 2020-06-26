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
import kotlinx.android.synthetic.main.fragment_filter_1.*
import kotlinx.android.synthetic.main.fragment_filter_2.*

class FilterTwoFragment : Fragment() {
    private val viewModel: SharedFilterFragmentViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_2, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.blurBitmap.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                img_filter_2.setImageBitmap(it)

            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar2.visibility = View.VISIBLE
            } else {
                progressBar2.visibility = View.GONE
            }
        })


    }

    override fun onStop() {
        super.onStop()
        viewModel.isLoading.removeObservers(viewLifecycleOwner)
        viewModel.blurBitmap.removeObservers(viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.e("myapp", "Start blur")

        if (viewModel.blurBitmap.value == null) {
            viewModel.makeBlur(FilterActivity.inputPath, requireContext())

        }


    }
}