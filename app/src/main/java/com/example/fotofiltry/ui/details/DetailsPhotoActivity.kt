package com.example.fotofiltry.ui.details

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fotofiltry.R
import kotlinx.android.synthetic.main.activity_details_photo.*

class DetailsPhotoActivity : AppCompatActivity() {
    var id: Int = 0
    lateinit var detailsViewModel: DetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(R.layout.activity_details_photo)
        setUpToolbar()
        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        detailsViewModel.photo.observe(this, Observer {
            Log.e("myapp","Loaded")
            if (it != null) {
                titleContent.text = it.title
                dateContent.text = it.date
                val rawTakenImage = BitmapFactory.decodeFile(Uri.decode(it.location))
                detailsImageView.setImageBitmap(rawTakenImage)
            }
        })
        detailsViewModel.isLoading.observe(this, Observer {
            if(it){
                progressBar4.visibility = View.VISIBLE
            }else{
                progressBar4.visibility = View.GONE
            }
        })
        detailsViewModel.isDeleted.observe(this, Observer {
            if(it){
                finish()
            }
        })
        id = intent.extras?.getInt("photoId")!!
        detailsViewModel.getDetailsById(id)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }
        if(item.itemId == R.id.deletePhoto){
            detailsViewModel.deletePhoto(id)

        }

        return true

    }
    private fun setUpToolbar() {

        supportActionBar?.title = "Details"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#999999")))


    }


}