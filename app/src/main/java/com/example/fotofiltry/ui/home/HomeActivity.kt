package com.example.fotofiltry.ui.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fotofiltry.R
import com.example.fotofiltry.data.PhotoModel
import com.example.fotofiltry.ui.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var itemList: MutableList<PhotoModel>
    lateinit var homeAdapter: HomeItemAdapter
    lateinit var homeViewModel: HomeViewModel
    private val newPhotoActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        //homeViewModel.getPhotos()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.w("insert", "Jestem-2" )
        if (requestCode == newPhotoActivityRequestCode && resultCode == Activity.RESULT_OK) {
            Log.w("insert", "Jestem-1" )
            val it = intent?.extras?.getString(CameraActivity.EXTRA_REPLY)
            Log.w("insert", "Jestem-0.7$it")
            val photoModel = PhotoModel("photo", SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.ENGLISH).format(System.currentTimeMillis()), it!!)
            Log.w("insert", "Jestem-0.5" )
            homeViewModel.insert(photoModel)
            homeAdapter.notifyDataSetChanged()
//            data?.getStringExtra(CameraActivity.EXTRA_REPLY)?.let {
//                Log.w("insert", "Jestem-0.7" )
//                val photoModel = PhotoModel("photo", SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.ENGLISH).format(System.currentTimeMillis()), it)
//                Log.w("insert", "Jestem-0.5" )
//                homeViewModel.insert(photoModel)
//                homeAdapter.notifyDataSetChanged()
//            }
            Log.w("insert", "Jestem10" )
        } else {
            Toast.makeText(
                applicationContext,
                "Not saved",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()

        itemList = mutableListOf()
        homeAdapter = HomeItemAdapter(itemList, supportFragmentManager)

        rv_home.apply{
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = homeAdapter
        }
        homeViewModel.item.observe(this, Observer {
            homeAdapter.setList(it)
        })
    }

    private fun setUpToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.home_toolbar)
        setSupportActionBar(toolbar)
        toolbar?.title = "Android"
        toolbar?.subtitle = "Sub"
        toolbar?.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_camera)
        toolbar?.setNavigationOnClickListener {
            val intent = Intent(this, CameraActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "message")
            }
            startActivityForResult(intent, 1)
        }
    }
}
