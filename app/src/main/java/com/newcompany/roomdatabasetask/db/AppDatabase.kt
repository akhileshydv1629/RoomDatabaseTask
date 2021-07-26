package com.newcompany.roomdatabasetask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
  abstract class AppDatabase:RoomDatabase() {
  abstract fun  getUserDAO(): UserDAO
    companion object{
      private var dbInstance:AppDatabase? =null

      fun getDbInstance(context: Context):AppDatabase
      {
        if(dbInstance==null)
        {
          dbInstance= Room.databaseBuilder<AppDatabase>(context.applicationContext,
                  AppDatabase::class.java,"db_test"
          ).allowMainThreadQueries()
                  .build()
        }
          return dbInstance!!
      }
    }





}