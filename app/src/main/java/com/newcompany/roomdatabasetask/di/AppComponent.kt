package com.newcompany.roomdatabasetask.di

import com.newcompany.roomdatabasetask.MainActivityViewModel
import com.newcompany.roomdatabasetask.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject( newsViewModell: NewsViewModel)
}