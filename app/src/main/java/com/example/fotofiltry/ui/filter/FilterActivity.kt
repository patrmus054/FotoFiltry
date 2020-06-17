package com.example.fotofiltry.ui.filter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.AlarmClock
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fotofiltry.R
import com.example.fotofiltry.data.PhotoModel
import com.example.fotofiltry.ui.camera.CameraActivity

class FilterActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "com.example.fotofiltry.ui.filter.ui.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        setUpToolbar()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_filter_1, R.id.navigation_filter_2, R.id.navigation_filter_3
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun setUpToolbar(){
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#999999")))
    }
}