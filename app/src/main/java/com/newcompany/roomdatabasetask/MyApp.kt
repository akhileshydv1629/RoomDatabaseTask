package com.newcompany.roomdatabasetask

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.downloader.PRDownloader
import com.newcompany.roomdatabasetask.di.AppComponent
import com.newcompany.roomdatabasetask.di.AppModule
import com.newcompany.roomdatabasetask.di.DaggerAppComponent
import com.newcompany.roomdatabasetask.utils.BindingComponent
import dagger.Component

class MyApp: Application(){

    lateinit var appCompo: AppComponent


    override fun onCreate() {
        super.onCreate()
        PRDownloader.initialize(getApplicationContext());
        appCompo = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        DataBindingUtil.setDefaultComponent(BindingComponent())
    }

    fun  getAppComponent():AppComponent{
        return appCompo
    }

}