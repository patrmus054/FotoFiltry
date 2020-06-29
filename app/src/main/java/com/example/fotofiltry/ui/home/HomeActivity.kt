package com.example.fotofiltry.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fotofiltry.R
import com.example.fotofiltry.data.PhotoModel
import com.example.fotofiltry.ui.camera.CameraActivity
import com.example.fotofiltry.ui.filter.FilterActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    var itemList: MutableList<PhotoModel> = mutableListOf()
    lateinit var homeAdapter: HomeItemAdapter
    lateinit var homeViewModel: HomeViewModel
    private val newPhotoActivityRequestCode = 1
    private val newFilterActivityRequestCode = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        homeViewModel.getAllPhotos()
        homeAdapter = HomeItemAdapter(itemList, supportFragmentManager)

        rv_home.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = homeAdapter
        }
        homeViewModel.item.observe(this, Observer {
            homeAdapter.setList(it)
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                newPhotoActivityRequestCode -> {
                    val it = data?.extras?.getString(CameraActivity.EXTRA_REPLY)
                    val photoModel = PhotoModel(
                        "photo",
                        SimpleDateFormat(
                            "yyyy-MM-dd-HH-mm-ss-SSS",
                            Locale.ENGLISH
                        ).format(System.currentTimeMillis()),
                        it!!
                    )
                    homeViewModel.insert(photoModel)
                    homeAdapter.notifyDataSetChanged()
                    val intent = Intent(this, FilterActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, photoModel.location)
                    }
                    startActivityForResult(intent, 2)
                }
                newFilterActivityRequestCode -> {
                    val it = data?.extras?.getString(FilterActivity.EXTRA_REPLY)
                    val photoModel = PhotoModel(
                        "photo",
                        SimpleDateFormat(
                            "yyyy-MM-dd-HH-mm-ss-SSS",
                            Locale.ENGLISH
                        ).format(System.currentTimeMillis()),
                        it!!
                    )
                    homeViewModel.update(photoModel)
                    homeAdapter.notifyDataSetChanged()
                }
                else -> Toast.makeText(
                    applicationContext,
                    "request code is neither 1 nor 2",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Not saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()

       // itemList = mutableListOf()

        homeViewModel.getAllPhotos()
    }


    private fun setUpToolbar() {

        supportActionBar?.title = "Home"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#999999")))


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cameraBtn) {
            val intent = Intent(this, CameraActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "message")
            }
            startActivityForResult(intent, 1)
        }
        return super.onOptionsItemSelected(item)
    }

}
