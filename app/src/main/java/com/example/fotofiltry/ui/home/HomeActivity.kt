package com.example.fotofiltry.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.GridLayout
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

class HomeActivity : AppCompatActivity() {

    lateinit var itemList: MutableList<PhotoModel>
    lateinit var homeAdapter: HomeItemAdapter
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.getPhotos()

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
        toolbar?.title = "Androidly"
        toolbar?.subtitle = "Sub"
        toolbar?.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_camera)
        toolbar?.setNavigationOnClickListener {
            val intent = Intent(this, CameraActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "message")
            }
            startActivity(intent)
        }
    }
}
