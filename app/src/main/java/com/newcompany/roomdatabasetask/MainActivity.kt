package com.newcompany.roomdatabasetask

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.newcompany.roomdatabasetask.databinding.ActivityMainBinding
import com.newcompany.roomdatabasetask.databinding.ListUserItemsBinding

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    lateinit var viewModelNews: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =ViewModelProvider(this).get( MainActivityViewModel::class.java)
        viewModelNews =ViewModelProvider(this).get( NewsViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        // Initialize PRDownloader with read and connection timeout
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(applicationContext, config)
    }

}