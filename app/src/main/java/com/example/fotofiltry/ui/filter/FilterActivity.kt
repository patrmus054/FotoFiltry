package com.example.fotofiltry.ui.filter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.Menu
import android.widget.Toast
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
import com.example.fotofiltry.ui.home.HomeActivity

class FilterActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "com.example.fotofiltry.ui.filter.ui.REPLY"
        var inputPath = ""
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
        inputPath = intent?.extras?.get(EXTRA_MESSAGE).toString()

    }
    private fun setUpToolbar(){
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#999999")))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_filter_menu,menu)

        return super.onCreateOptionsMenu(menu)

    }
    fun backToHome(){
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, inputPath)
        setResult(Activity.RESULT_OK, replyIntent)
        Toast.makeText(baseContext, "Saved", Toast.LENGTH_SHORT).show()
        Log.d("myapp", "saved")
        finish()

    }

}